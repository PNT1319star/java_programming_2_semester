package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithArgument implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand command;
    private final String argument;

    public SerializedCommandWithArgument(AbstractCommand command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }
}
