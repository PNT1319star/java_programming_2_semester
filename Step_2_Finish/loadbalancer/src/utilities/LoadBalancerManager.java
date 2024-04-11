package utilities;

import clientconnector.ClientCommunicator;
import clientconnector.ReceiverFromClient;
import clientconnector.SenderToClient;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerSocketException;
import file.FileProcess;
import net.sourceforge.yamlbeans.YamlException;
import serverconnector.ReceiverFromServer;
import serverconnector.SenderToServer;
import serverconnector.ServerCommunicator;
import utility.ConsolePrinter;

import java.io.*;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LoadBalancerManager {
    private static final int CLIENT_PORT = 2222;
    private static final int CONNECTION_TIMEOUT = 50 * 1000;
    private static ServerCommunicator serverCommunicator;
    private static Stack<ServerCommunicator> serverCommunicatorStack;
    private static List<ServerCommunicator> serverCommunicatorList;

    public static void start(String pathToFile) {
        try {
            FileProcess.readFile(pathToFile);
            serverCommunicatorList = FileProcess.getServerCommunicatorList();
            serverCommunicatorStack = new Stack<>();
            serverCommunicatorStack.addAll(serverCommunicatorList);
            for(ServerCommunicator serverCommunicator1: serverCommunicatorList) {
                serverCommunicator1.startConnectingToServer();
            }
            ClientCommunicator clientCommunicator = new ClientCommunicator(CLIENT_PORT, CONNECTION_TIMEOUT);
            clientCommunicator.openServerSocket();
            Thread heartbeatThread = startHeartbeatCheck();
            heartbeatThread.start();
            Thread handleConnectionFromClient = new Thread(() -> {
                try {
                    while (true) {
                        clientCommunicator.handleClientConnection();
                        ReceiverFromClient.setClientSocket(clientCommunicator.getClientSocket());
                        SenderToClient.setClientSocket(clientCommunicator.getClientSocket());
                        serverCommunicator = serverCommunicatorStack.pop();
                        ReceiverFromServer receiverFromServer = new ReceiverFromServer(serverCommunicator.getDatagramSocket());
                        SenderToServer.setServerCommunicator(serverCommunicator);
                        byte[] transferredRequest = ReceiverFromClient.receiveFromClient();
                        SenderToServer.sendToServer(transferredRequest);
                        byte[] transferredResponse = receiverFromServer.receive();
                        SenderToClient.sentToClient(transferredResponse);
                    }
                } catch (ConnectionErrorException | SocketTimeoutException e) {
                    checkQuit();
                    ConsolePrinter.printError("The server cannot be started!");
                    heartbeatThread.interrupt();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            handleConnectionFromClient.start();
        } catch (OpeningServerSocketException e) {
            ConsolePrinter.printError("The server cannot be started!");
        } catch (YamlException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkQuit() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject("quit");
            outputStream.flush();
            byte[] data = byteArrayOutputStream.toByteArray();
            for(ServerCommunicator serverCommunicator1 : serverCommunicatorList) {
                DatagramPacket packet = new DatagramPacket(data, data.length, serverCommunicator1.getDatagramSocket().getInetAddress(), serverCommunicator1.getServerPort());
                serverCommunicator1.getDatagramSocket().send(packet);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String sendAndReceiveHeartBeat(ServerCommunicator serverCommunicator, int port) {
        String answer = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject("alive");
            outputStream.flush();
            byte[] data = byteArrayOutputStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverCommunicator.getDatagramSocket().getInetAddress(), port);
            serverCommunicator.getDatagramSocket().send(packet);
            ReceiverFromServer receiverFromServer = new ReceiverFromServer(serverCommunicator.getDatagramSocket());
            byte[] heartBeat = receiverFromServer.receive();
            answer = new String(heartBeat);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return answer;
    }

    private static Thread startHeartbeatCheck() {
        return new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5); // Sleep for 5 seconds between each heartbeat check
                    for (ServerCommunicator serverCommunicator1 : serverCommunicatorList) {
                        String heartBeat = sendAndReceiveHeartBeat(serverCommunicator1, serverCommunicator1.getServerPort());
                        if (heartBeat.equals("alive")) {
                            serverCommunicatorStack.push(serverCommunicator1);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

}