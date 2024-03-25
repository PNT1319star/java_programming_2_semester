package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObjectAndArgument implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand command;
    private final Object objectToSend;
    private final String argument;


    public SerializedCommandWithObjectAndArgument(AbstractCommand command, Object objectToSend, String argument) {
        this.command = command;
        this.objectToSend = objectToSend;
        this.argument = argument;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public Object getObject() {
        return objectToSend;
    }

    public String getArgument() {
        return argument;
    }
}
