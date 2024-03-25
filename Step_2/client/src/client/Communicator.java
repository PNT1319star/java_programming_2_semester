package client;

import exceptions.ConnectionErrorException;
import exceptions.NotInDeclaredLimitsException;
import utility.ConsolePrinter;

import java.io.Console;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Communicator {
    private SocketChannel socketChannel;
    private final String host;
    private final int port;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private int reconnectionTimeout;


    public Communicator(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts) {
        this.host = host;
        this.port = port;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.reconnectionTimeout = reconnectionTimeout;

    }

    public void startCommunication() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) ConsolePrinter.printResult("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            ConsolePrinter.printResult("Connecting to server...");
        } catch (IOException e) {
            ConsolePrinter.printError("An error occurred while connecting to the server!");
            throw new ConnectionErrorException();
        } catch (IllegalArgumentException exception) {
            ConsolePrinter.printError("The server address was entered incorrectly!");
            throw new NotInDeclaredLimitsException();
        }
    }

    public void endCommunication() {
        if (socketChannel.socket() != null && !socketChannel.socket().isClosed()) {
            try {
                socketChannel.close();
                ConsolePrinter.printResult("Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socketChannel.socket();
    }
    public int getReconnectionAttempts() {
        return reconnectionAttempts;
    }
    public void setReconnectionAttempts(int reconnectionAttempts) {
        this.reconnectionAttempts = reconnectionAttempts;
    }
}
