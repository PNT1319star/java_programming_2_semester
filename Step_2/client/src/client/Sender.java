package client;

import client.Communicator;
import commands.serializedcommands.SerializedCommandWithArgument;
import commands.serializedcommands.SerializedCommandWithObjectAndArgument;
import commands.serializedcommands.SerializedCommandWithObject;
import commands.serializedcommands.SerializedSimpleCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {
    private final Socket socket;

    public Sender(Communicator communicator) {
        this.socket = communicator.getSocket();
    }

    public void sendObject(SerializedCommandWithArgument serializedArgumentCommand) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(serializedArgumentCommand);
        outputStream.flush();
    }

    public void sendObject(SerializedCommandWithObjectAndArgument serializedCommandWithObjectAndArgument) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(serializedCommandWithObjectAndArgument);
        outputStream.flush();
    }

    public void sendObject(SerializedCommandWithObject serializedCommandWithObject) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(serializedCommandWithObject);
        outputStream.flush();
    }

    public void sendObject(SerializedSimpleCommand serializedSimpleCommand) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(serializedSimpleCommand);
        outputStream.flush();
    }

    public Socket getSocket() {
        return socket;
    }
}
