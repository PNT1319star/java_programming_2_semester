package clientconnector;

import utility.ConsolePrinter;

import java.io.*;
import java.net.Socket;

public class ReceiverFromClient {
    private final Socket clientSocket;
    public ReceiverFromClient(Socket socket) {
        this.clientSocket = socket;
    }
    public byte[] receiveFromClient() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = inputStream.read(buffer);
        byte[] data = new byte[bytesRead];
        System.arraycopy(buffer, 0, data, 0, bytesRead);
        return data;
    }
}
