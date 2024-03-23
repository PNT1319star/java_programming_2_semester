package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serializable;

public class SerializedCommandWithArgument implements Serializable {
    private AbstractCommand command;
    private String argument;

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
