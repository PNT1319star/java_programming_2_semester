package server;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import database.*;
import exceptions.ConnectionErrorException;
import exceptions.ConnectionTimeOutException;
import exceptions.OpeningServerSocketException;
import processing.CommandManager;
import processing.ServerCommandProcessor;
import processing.ServerProcessor;
import utilities.CollectionManager;

import java.io.IOException;

public class Server {
    private final String sPort;
    private final String databaseAddress;
    private final String databaseHost;
    private final String databasePassword;
    private final int CONNECTION_TIMEOUT = 6000 * 1000;

    public Server(String port, String databaseAddress, String databaseHost, String databasePassword) {
        this.sPort = port;
        this.databaseAddress = databaseAddress;
        this.databaseHost = databaseHost;
        this.databasePassword = databasePassword;
    }

    public void run() {
        try {
            int port = Integer.parseInt(sPort);
            Communicator communicator = new Communicator(port, CONNECTION_TIMEOUT);
            DatabaseConnector databaseConnector = new DatabaseConnector(databaseAddress, databaseHost, databasePassword);
            communicator.openServerSocket();
            databaseConnector.connectToDatabase();
            while (true) {
                communicator.handleClientConnection();
                Receiver receiver = new Receiver(communicator.getClientSocket());
                Sender sender = new Sender(communicator.getClientSocket());

                DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseConnector);
                DatabaseHandler databaseHandler = new DatabaseHandler(databaseConnector);
                DatabaseSessionManager databaseSessionManager = new DatabaseSessionManager(databaseConnector);
                DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseConnector, databaseUserManager, databaseHandler);
                CollectionManager.setDatabaseCollectionManager(databaseCollectionManager);
                CollectionManager.loadCollectionFromDatabase();
                ServerCommandProcessor serverCommandProcessor = new ServerCommandProcessor(databaseUserManager);
                ServerProcessor serverProcessor = new ServerProcessor(receiver, sender, databaseSessionManager);
                CommandManager.invokeCommand(serverCommandProcessor);
                serverProcessor.decodeAndProcessCommand();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (OpeningServerSocketException | ConnectionErrorException e) {
            throw new RuntimeException(e);
        } catch (ConnectionTimeOutException e) {
            System.exit(0);
        }
    }

}