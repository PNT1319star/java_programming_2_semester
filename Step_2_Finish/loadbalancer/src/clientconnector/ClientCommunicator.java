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

    private void openServerSocket() throws OpeningServerSocketException {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(soTimeout);
        } catch (IllegalArgumentException exception) {
            ConsolePrinter.printError("Port " + port + " is out of range!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while trying to use port " + port + " !");
            throw new OpeningServerSocketException();
        }
    }

    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            ConsolePrinter.printInformation("Listening to port '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            ConsolePrinter.printResult("The connection from the client was successfully established!");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            ConsolePrinter.printError("Connection timed out!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while connecting to the client!");
            throw new ConnectionErrorException();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            ConsolePrinter.printResult("The server has been shut down successfully.");
        } catch (ClosingSocketException exception) {
            ConsolePrinter.printError("It is impossible to shut down a server that has not yet started!");
        } catch (IOException exception) {
            ConsolePrinter.printError("An error occurred while shutting down the server!");
        }
    }

    public void connect() {
        try {
            openServerSocket();
            try  {
                this.clientSocket = connectToClient();
            } catch (ConnectionErrorException | SocketTimeoutException exception) {
                ConsolePrinter.printError("Time out!");
            } catch (IOException exception) {
                ConsolePrinter.printError("An error occurred while trying to terminate the connection to the client!");
            }
//            closeServerSocket();
        } catch (OpeningServerSocketException exception) {
            ConsolePrinter.printError("The server cannot be started!");
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
