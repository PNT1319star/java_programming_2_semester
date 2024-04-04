package clientconnector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SenderToClient {
    private static Socket clientSocket;

    public static void sentToClient(byte[] object) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(object);
        outputStream.flush();
        outputStream.close();
    }

    public static void setClientSocket(Socket socket) {
        clientSocket = socket;
    }
}
