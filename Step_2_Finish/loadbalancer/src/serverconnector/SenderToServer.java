package serverconnector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SenderToServer {
    private final DatagramSocket datagramSocket;
    private final ServerCommunicator serverCommunicator;

    public SenderToServer(ServerCommunicator serverCommunicator) {
        this.serverCommunicator = serverCommunicator;
        this.datagramSocket = serverCommunicator.getDatagramSocket();
    }

    public void sendToServer(byte[] data) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, datagramSocket.getInetAddress(), serverCommunicator.getServerPort());
        datagramSocket.send(datagramPacket);
    }
}
