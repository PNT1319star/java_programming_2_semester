package connector;

import processing.serializedcommands.SerializedCommandWithObject;
import utility.ConsolePrinter;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {
    private final Socket socket;
    public Sender(Socket socket) {
        this.socket = socket;
    }
    public void sendObject(SerializedCommandWithObject serializedCommandWithObject) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(serializedCommandWithObject);
        outputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        socket.getOutputStream().write(data);
        outputStream.close();
        byteArrayOutputStream.close();
    }

}
