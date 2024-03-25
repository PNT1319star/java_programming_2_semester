package commands.serializedcommands;

import commands.AbstractCommand2;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObjectAndArgument implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand2 command;
    private final Object objectToSend;
    private final String argument;


    public SerializedCommandWithObjectAndArgument(AbstractCommand2 command, Object objectToSend, String argument) {
        this.command = command;
        this.objectToSend = objectToSend;
        this.argument = argument;
    }

    public AbstractCommand2 getCommand() {
        return command;
    }

    public Object getObject() {
        return objectToSend;
    }

    public String getArgument() {
        return argument;
    }
}
