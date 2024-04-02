package loadbalancerconnector;

import utility.ConsolePrinter;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver {
    private Object object;
    private final DatagramChannel datagramChannel;
    public static SocketAddress socketAddress;

    public Receiver(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public Object receive() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (true) {
                socketAddress = datagramChannel.receive(buffer);
                if (socketAddress != null) {
                    buffer.flip();
                    byte[] ib = new byte[buffer.remaining()];
                    buffer.get(ib);
                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(ib);
                        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
                        object = inputStream.readObject();
                    } catch (EOFException | SocketException exception) {
                        System.out.println("Проблемы с клиентом.");
                    } catch (ClassNotFoundException exception) {
                        ConsolePrinter.printError(object);
                        exception.printStackTrace();
                    }
                }
            }
            } catch(IOException exception){
                ConsolePrinter.printError("Something went wrong!");
                exception.printStackTrace();
            }
            return object;
        }
    }
