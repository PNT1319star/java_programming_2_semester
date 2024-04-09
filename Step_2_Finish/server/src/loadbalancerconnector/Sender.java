package loadbalancerconnector;

import utility.ConsolePrinter;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    private static DatagramChannel datagramChannel;

    public static void send(byte[] response) throws IOException {
        datagramChannel.send(ByteBuffer.wrap(response),Receiver.socketAddress);
    }
    public static void setDatagramChannel(DatagramChannel datagramChannel) {
        Sender.datagramChannel = datagramChannel;
    }

}
