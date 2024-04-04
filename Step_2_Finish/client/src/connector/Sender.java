package connector;

import processing.serializedcommands.SerializedCommandWithArgument;
import processing.serializedcommands.SerializedCommandWithObject;
import processing.serializedcommands.SerializedCommandWithObjectAndArgument;
import processing.serializedcommands.SerializedSimpleCommand;

import java.io.ByteArrayOutputStream;
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

    public void sendObject(SerializedSimpleCommand serializedSimpleCommand) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(serializedSimpleCommand);
        outputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        socket.getOutputStream().write(data);
        outputStream.close();
        byteArrayOutputStream.close();
    }

    public void sendObject(SerializedCommandWithArgument serializedCommandWithArgument) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(serializedCommandWithArgument);
        outputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        socket.getOutputStream().write(data);
        outputStream.close();
        byteArrayOutputStream.close();
    }

    public void sendObject(SerializedCommandWithObjectAndArgument serializedCommandWithObjectAndArgument) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(serializedCommandWithObjectAndArgument);
        outputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        socket.getOutputStream().write(data);
        outputStream.close();
        byteArrayOutputStream.close();
    }
}
