package serverconnector;

import utility.ConsolePrinter;

import java.net.*;

public class ServerCommunicator {
    private DatagramSocket datagramSocket;
    private final String HOST = "localhost";
    private int serverPort;

    public ServerCommunicator(int port) {
        this.serverPort = port;
    }

    public void startConnectingToServer() {
        try {
            ConsolePrinter.printResult("Connecting to server....");
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(InetAddress.getByName(HOST), serverPort);
            ConsolePrinter.printResult("The connection to the server was successfully established!");
        } catch (SocketException exception) {
            ConsolePrinter.printError("An error occurred while connecting to the server!");
            exception.printStackTrace();
        } catch (UnknownHostException exception) {
            ConsolePrinter.printError("Something went wrong with server!");
            System.exit(0);
        }
    }

    public void stopConnectingToServer() {
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }
    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }
    public int getServerPort() {
        return serverPort;
    }
}
