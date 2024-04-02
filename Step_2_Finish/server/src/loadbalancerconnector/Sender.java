package loadbalancerconnector;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    private DatagramChannel datagramChannel;

    public Sender(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }
    public void send(byte[] response) throws IOException {
        datagramChannel.send(ByteBuffer.wrap(response),Receiver.socketAddress);
    }

}
