package server;

import commands.AbstractCommand;
import commands.serializedcommands.SerializedCommandWithArgument;
import commands.serializedcommands.SerializedCommandWithObject;
import commands.serializedcommands.SerializedCommandWithObjectAndArgument;
import commands.serializedcommands.SerializedSimpleCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CommandReceiver {
    private final Socket socket;

    public CommandReceiver(Socket socket) {
        this.socket = socket;
    }

    public void receiveAndDecode() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Object receivedObject = inputStream.readObject();
        decode(receivedObject);
    }

    private void decode(Object object) throws IOException {
        if (object instanceof SerializedSimpleCommand) {
            SerializedSimpleCommand simpleCommand = (SerializedSimpleCommand) object;
            AbstractCommand command = simpleCommand.getCommand();
            String arg = "";
            command.execute(arg,socket);
        }
        if (object instanceof SerializedCommandWithArgument) {
            SerializedCommandWithArgument argumentCommand = (SerializedCommandWithArgument) object;
            AbstractCommand command = argumentCommand.getCommand();
            String arg = argumentCommand.getArgument();
            command.execute(arg,socket);
        }
        if (object instanceof SerializedCommandWithObject) {
            SerializedCommandWithObject objectCommand = (SerializedCommandWithObject) object;
            AbstractCommand command = objectCommand.getCommand();
            Object arg = objectCommand.getObject();
            command.execute(arg, socket);
        }
        if (object instanceof SerializedCommandWithObjectAndArgument) {
            SerializedCommandWithObjectAndArgument combinedCommand = (SerializedCommandWithObjectAndArgument) object;
            AbstractCommand command = combinedCommand.getCommand();
            command.execute(combinedCommand, socket);
        }
    }
}
