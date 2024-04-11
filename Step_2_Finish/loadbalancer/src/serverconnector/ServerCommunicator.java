package serverconnector;

import utility.ConsolePrinter;

import java.net.*;

public class ServerCommunicator {
    private DatagramSocket datagramSocket;
    private final String host;
    private final int serverPort;

    public ServerCommunicator(String host, int port) {
        this.host = host;
        this.serverPort = port;
    }

    public void startConnectingToServer() {
        try {
            ConsolePrinter.printResult("Connecting to server....");
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(InetAddress.getByName(host), serverPort);
            ConsolePrinter.printResult("The connection to the server was successfully established!");
        } catch (SocketException exception) {
            ConsolePrinter.printError("An error occurred while connecting to the server!");
            exception.printStackTrace();
        } catch (UnknownHostException exception) {
            ConsolePrinter.printError("Something went wrong with server!");
            System.exit(0);
        }
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }
    public int getServerPort() {
        return serverPort;
    }
}
