package client;

import commands.serializedcommands.SerializedCommandWithArgument;
import commands.serializedcommands.SerializedCommandWithObjectAndArgument;
import commands.serializedcommands.SerializedCommandWithObject;
import commands.serializedcommands.SerializedSimpleCommand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {
    private final Socket socket;
    private final ObjectOutputStream outputStream;

    public Sender(Communicator communicator) throws IOException {
        this.socket = communicator.getSocket();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        this.outputStream = new ObjectOutputStream(bufferedOutputStream);

    }

    public void sendObject(SerializedCommandWithArgument serializedArgumentCommand) throws IOException {
        outputStream.writeObject(serializedArgumentCommand);
        outputStream.flush();
    }

    public void sendObject(SerializedCommandWithObjectAndArgument serializedCommandWithObjectAndArgument) throws IOException {
        outputStream.writeObject(serializedCommandWithObjectAndArgument);
        outputStream.flush();
    }

    public void sendObject(SerializedCommandWithObject serializedCommandWithObject) throws IOException {
        outputStream.writeObject(serializedCommandWithObject);
        outputStream.flush();
    }

    public void sendObject(SerializedSimpleCommand serializedSimpleCommand) throws IOException {
        outputStream.writeObject(serializedSimpleCommand);
        outputStream.flush();
    }

    public Socket getSocket() {
        return socket;
    }
}
