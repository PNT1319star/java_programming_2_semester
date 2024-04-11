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

    public Server(String port) {
        this.sPort = port;
    }

    public void run() {
        try {
            Communicator communicator = new Communicator(sPort);
            communicator.startRunning();
            DatagramChannel datagramChannel = communicator.getDatagramChannel();
            Receiver receiver = new Receiver(datagramChannel);
            Sender.setDatagramChannel(datagramChannel);
            ServerProcessor serverProcessor = new ServerProcessor(receiver);
            while (true) {
                serverProcessor.decodeAndProcessCommand();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}