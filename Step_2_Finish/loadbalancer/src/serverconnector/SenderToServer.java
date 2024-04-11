package serverconnector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SenderToServer {
    private static ServerCommunicator serverCommunicator;

    public static void sendToServer(byte[] data) throws IOException {
        DatagramSocket datagramSocket = serverCommunicator.getDatagramSocket();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, datagramSocket.getInetAddress(), serverCommunicator.getServerPort());
        datagramSocket.send(datagramPacket);
    }
    public static void setServerCommunicator(ServerCommunicator serverCommunicator) {
        SenderToServer.serverCommunicator = serverCommunicator;
    }
}
