package utilities;

import clientconnector.ClientCommunicator;
import clientconnector.ReceiverFromClient;
import clientconnector.SenderToClient;
import serverconnector.ReceiverFromServer;
import serverconnector.SenderToServer;
import serverconnector.ServerCommunicator;
import utility.ConsolePrinter;

import java.io.IOException;
import java.net.DatagramSocket;

public class LoadBalancerManager {
    private static final int CLIENT_PORT = 2222;
    private static final int SERVER_PORT_1 = 5000;
    private static final int CONNECTION_TIMEOUT = 50 * 1000;

    public static void start() {
        try {
            ServerCommunicator serverCommunicator = new ServerCommunicator(SERVER_PORT_1);
            serverCommunicator.startConnectingToServer();
            ClientCommunicator clientCommunicator = new ClientCommunicator(CLIENT_PORT, CONNECTION_TIMEOUT);
            clientCommunicator.connect();
            ReceiverFromClient receiverFromClient = new ReceiverFromClient(clientCommunicator.getClientSocket());
            SenderToClient senderToClient = new SenderToClient(clientCommunicator.getClientSocket());
            ReceiverFromServer receiverFromServer = new ReceiverFromServer(serverCommunicator.getDatagramSocket());
            SenderToServer senderToServer = new SenderToServer(serverCommunicator);
            byte[] transferredRequest = receiverFromClient.receiveFromClient();
            senderToServer.sendToServer(transferredRequest);
            byte[] transferredResponse = receiverFromServer.receive();
            senderToClient.sentToClient(transferredResponse);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
