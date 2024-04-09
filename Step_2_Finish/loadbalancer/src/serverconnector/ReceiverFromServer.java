package serverconnector;

import utility.ConsolePrinter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ReceiverFromServer {
    private final DatagramSocket datagramSocket;

    public ReceiverFromServer(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public byte[] receive() {
        byte[] answer = null;
        try {
            byte[] ib = new byte[4096];
            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            datagramSocket.setSoTimeout(5000);
            datagramSocket.receive(ip);
            int actualLength = ip.getLength();
            byte[] receivedData = new byte[actualLength];
            System.arraycopy(ib, 0, receivedData,0, actualLength);
            answer = receivedData;
        } catch (IOException e) {
            ConsolePrinter.printError("The server has problems, the command will not be executed.");
        }
        return answer;
    }
}
