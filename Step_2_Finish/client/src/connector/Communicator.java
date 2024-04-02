package connector;

import exceptions.ConnectionErrorException;
import exceptions.NotInDeclaredLimitsException;
import utility.ConsolePrinter;

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

    public Communicator(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
    }

    private void connectToLoadBalancer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) ConsolePrinter.printResult("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            ConsolePrinter.printResult("The connection to the load balancer was successfully established!");
            ConsolePrinter.printResult("Waiting for permission to share data...");
            ConsolePrinter.printResult("Permission to share data has been received!");
        } catch (IllegalArgumentException exception) {
            ConsolePrinter.printError("The server address was entered incorrectly!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while connecting to the load balancer!");
            throw new ConnectionErrorException();
        }
    }

    public void connect() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToLoadBalancer();
                    processingStatus = false;
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        ConsolePrinter.printError("The number of connection attempts has been exceeded!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        ConsolePrinter.printError("Connection timeout '\" + reconnectionTimeout +\n" + "\"' is outside the range of possible values!");
                    } catch (Exception timeoutException) {
                        ConsolePrinter.printError("An error occurred while trying to wait for a connection!");
                        ConsolePrinter.printInformation("Reconnection will be made immediately.");
                    }
                }
                reconnectionAttempts++;
            }
//            if (socketChannel != null) socketChannel.close();
//            ConsolePrinter.printResult("The client's work has completed successfully.");
        } catch (NotInDeclaredLimitsException exception) {
            ConsolePrinter.printError("The client cannot be started!");
        }
//        } catch (IOException exception) {
//            ConsolePrinter.printError("An error occurred while trying to complete the connection to the server!");
//        }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

}
