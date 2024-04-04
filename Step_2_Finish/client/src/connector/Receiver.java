package connector;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receiver {
    private final Socket socket;
    public Receiver(Socket socket) {
        this.socket = socket;
    }
    public byte[] receive() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = inputStream.read(buffer);
        byte[] data = new byte[bytesRead];
        System.arraycopy(buffer, 0, data, 0, bytesRead);
        return data;
    }
}
