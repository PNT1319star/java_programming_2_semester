package server;

import file.CSVProcess;
import file.CSVReader;
import loadbalancerconnector.Communicator;
import loadbalancerconnector.Receiver;
import loadbalancerconnector.Sender;
import processing.ServerProcessor;
import utilities.CollectionManager;
import utility.ConsolePrinter;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

public class Server {
    private final String sPort;
    private final String pathToFile;

    public Server(String port, String pathToFile) {
        this.sPort = port;
        this.pathToFile = pathToFile;
    }

    public void run() {
        try {
            Communicator communicator = new Communicator(sPort);
            communicator.startRunning();
            DatagramChannel datagramChannel = communicator.getDatagramChannel();
            Receiver receiver = new Receiver(datagramChannel);
            Sender.setDatagramChannel(datagramChannel);
            ServerProcessor serverProcessor = new ServerProcessor(receiver);
            CSVProcess.setPathToFile(pathToFile);
            CollectionManager.getCollectionFromFile(pathToFile);
            while (true) {
                serverProcessor.decodeAndProcessCommand();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}