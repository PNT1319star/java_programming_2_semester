package utilities;

import clientconnector.ClientCommunicator;
import clientconnector.ReceiverFromClient;
import clientconnector.SenderToClient;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerSocketException;
import serverconnector.ReceiverFromServer;
import serverconnector.SenderToServer;
import serverconnector.ServerCommunicator;
import utility.ConsolePrinter;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class LoadBalancerManager {
    private static final int CLIENT_PORT = 2222;
    private static final int SERVER_PORT_1 = 5000;
    private static final int CONNECTION_TIMEOUT = 50 * 1000;

    public static void start() {
        try {
            ServerCommunicator serverCommunicator = new ServerCommunicator(SERVER_PORT_1);
            serverCommunicator.startConnectingToServer();
            ClientCommunicator clientCommunicator = new ClientCommunicator(CLIENT_PORT, CONNECTION_TIMEOUT);
            clientCommunicator.openServerSocket();
            Thread handleConnectionFromClient = new Thread(() -> {
                try {
                    while (true) {
                        clientCommunicator.handleClientConnection();
                        ReceiverFromClient.setClientSocket(clientCommunicator.getClientSocket());
                        SenderToClient.setClientSocket(clientCommunicator.getClientSocket());
                        ReceiverFromServer receiverFromServer = new ReceiverFromServer(serverCommunicator.getDatagramSocket());
                        SenderToServer senderToServer = new SenderToServer(serverCommunicator);
                        byte[] transferredRequest = ReceiverFromClient.receiveFromClient();
                        senderToServer.sendToServer(transferredRequest);
                        byte[] transferredResponse = receiverFromServer.receive();
                        SenderToClient.sentToClient(transferredResponse);
                    }
                } catch (ConnectionErrorException | SocketTimeoutException e) {
                    ConsolePrinter.printError("The server cannot be started!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            handleConnectionFromClient.start();
        } catch (OpeningServerSocketException e) {
            ConsolePrinter.printError("The server cannot be started!");
        }
    }

}
