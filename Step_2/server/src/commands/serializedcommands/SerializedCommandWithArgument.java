package commands.serializedcommands;

import commands.AbstractCommand2;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithArgument implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;

    private final AbstractCommand2 command;
    private final String argument;

    public SerializedCommandWithArgument(AbstractCommand2 command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public AbstractCommand2 getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }
}
