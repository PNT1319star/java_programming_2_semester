package loadbalancerconnector;

import utility.ConsolePrinter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Communicator {
    private final String sPort;
    private DatagramChannel datagramChannel;

    public Communicator(String port) {
        this.sPort = port;
    }

    public void startRunning() {
        try {
            int port = Integer.parseInt(sPort);
            SocketAddress address = new InetSocketAddress(port);
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            datagramChannel.socket().bind(address);
            ConsolePrinter.printResult("Server is running!");
        } catch (NumberFormatException | IOException exception) {
            ConsolePrinter.printError("Something went wrong!");
            exception.printStackTrace();
        }
    }

    public void stopRunning() {
        try {
            if (datagramChannel != null)
                datagramChannel.close();
        } catch (IOException exception) {
            ConsolePrinter.printError("Can not close datagram channel!");
            exception.printStackTrace();
        }
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }
}
