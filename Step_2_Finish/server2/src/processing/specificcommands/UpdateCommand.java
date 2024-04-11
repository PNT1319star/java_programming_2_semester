package processing.specificcommands;

import processing.ServerCommandProcessor;
import processing.serializedcommands.SerializedCommandWithObjectAndArgument;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class UpdateCommand extends AbstractCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            SerializedCommandWithObjectAndArgument updateCommand = (SerializedCommandWithObjectAndArgument) object;
            Object organization = updateCommand.getObject();
            String sID = updateCommand.getArgument();
            commandProcessor.update(sID, organization);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
