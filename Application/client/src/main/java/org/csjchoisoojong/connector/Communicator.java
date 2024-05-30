package org.csjchoisoojong.connector;

import org.csjchoisoojong.exceptions.ConnectionErrorException;
import org.csjchoisoojong.exceptions.NotInDeclaredLimitsException;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Communicator {
    private final String host;
    private final int port;
    private final int reconnectionTimeout;
    private int reconnectionAttempts;
    private final int maxReconnectionAttempts;
    private SocketChannel socketChannel;
    private boolean isConnected;

    public Communicator(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
    }

    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) ConsolePrinter.printResult("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            isConnected = true;
        } catch (IllegalArgumentException exception) {
            ConsolePrinter.printError("The server address was entered incorrectly!");
            isConnected = false;
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while connecting to the server!");
            isConnected = false;
            throw new ConnectionErrorException();
        }
    }

    public void connect() throws ConnectionErrorException {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = false;
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        throw new ConnectionErrorException();
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        ConsolePrinter.printError("Connection timeout " + reconnectionTimeout  + " is outside the range of possible values!");
                    } catch (Exception timeoutException) {
                        ConsolePrinter.printError("An error occurred while trying to wait for a connection!");
                        ConsolePrinter.printInformation("Reconnection will be made immediately.");
                    }
                }
                reconnectionAttempts++;
            }
        } catch (NotInDeclaredLimitsException exception) {
            ConsolePrinter.printError("The client cannot be started!");
        }
    }

    public void closeConnection() {
        try {
            if (socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            } else {
                ConsolePrinter.printError("Socket channel is not initialized or already closed.");
            }
        } catch (IOException e) {
            ConsolePrinter.printError("An error occurred while closing the socket connection: " + e.getMessage());
        }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
    public boolean isConnected(){
        return isConnected;
    }

}
