package clientconnector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SenderToClient {
    private final Socket clientSocket;
    public SenderToClient (Socket socket) {
        this.clientSocket = socket;
    }
    public void sentToClient(byte[] object) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(object);
        outputStream.flush();
        outputStream.close();
    }
}
