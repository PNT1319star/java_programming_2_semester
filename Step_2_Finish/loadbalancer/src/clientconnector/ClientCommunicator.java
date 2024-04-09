package clientconnector;

import exceptions.ClosingSocketException;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerSocketException;
import utility.ConsolePrinter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientCommunicator {
    public int port;
    public int soTimeout;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public ClientCommunicator(int port, int soTimeout) {
        this.port = port;
        this.soTimeout = soTimeout;
    }

    public void openServerSocket() throws OpeningServerSocketException {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
            ConsolePrinter.printResult("Server socket is opened at port " + port + ".");
        } catch (IllegalArgumentException exception) {
            ConsolePrinter.printError("Port " + port + " is out of range!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while trying to use port " + port + " !");
            throw new OpeningServerSocketException();
        }
    }

    public void handleClientConnection() throws ConnectionErrorException, SocketTimeoutException {
        try {
            ConsolePrinter.printInformation("Listening to port '" + port + "'...");
            clientSocket = serverSocket.accept();
            ReceiverFromClient.setClientSocket(clientSocket);
            ConsolePrinter.printResult("The connection from the client was successfully established!");
        } catch (SocketTimeoutException exception) {
            ConsolePrinter.printError("Connection timed out!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while connecting to the client!");
            throw new ConnectionErrorException();
        }
    }

//    private void closeServerSocket() {
//        try {
//            if (serverSocket == null) throw new ClosingSocketException();
//            serverSocket.close();
//            ConsolePrinter.printResult("The server has been shut down successfully.");
//        } catch (ClosingSocketException exception) {
//            ConsolePrinter.printError("It is impossible to shut down a server that has not yet started!");
//        } catch (IOException exception) {
//            ConsolePrinter.printError("An error occurred while shutting down the server!");
//        }
//    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
