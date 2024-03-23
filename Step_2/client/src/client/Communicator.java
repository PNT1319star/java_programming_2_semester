package client;

import utility.ConsolePrinter;

import java.io.IOException;
import java.net.Socket;

public class Communicator {
    private Socket socket;
    private final String host;
    private final int port;

    public Communicator(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startCommunication() {
        try {
            socket = new Socket(host, port);
            ConsolePrinter.printResult("Connecting to server...");
        } catch (IOException e) {
            ConsolePrinter.printError("Failed to connect to server.");
            e.printStackTrace();
        }
    }

    public void endCommunication() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
                ConsolePrinter.printResult("Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
