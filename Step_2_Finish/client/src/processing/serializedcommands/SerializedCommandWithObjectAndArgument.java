package processing.serializedcommands;

import processing.specificcommands.AbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObjectAndArgument implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand command;
    private final Object object;
    private final String argument;

    public SerializedCommandWithObjectAndArgument(AbstractCommand command, Object object, String argument) {
        this.command = command;
        this.object = object;
        this.argument = argument;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getArgument() {
        return argument;
    }
}
