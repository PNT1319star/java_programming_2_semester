package commands.specificcommands;

import commands.AbstractCommand;
import commands.serializedcommands.SerializedCommandWithObjectAndArgument;
import server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class UpdateCommand extends AbstractCommand implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object object, Socket socket) throws IOException {
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithObjectAndArgument updateCommand = (SerializedCommandWithObjectAndArgument) object;
        Object object1 = updateCommand.getObject();
        String argument = updateCommand.getArgument();
        receiver.update(argument, object1);
    }
}
