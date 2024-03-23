package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serializable;

public class SerializedSimpleCommand implements Serializable {
    private AbstractCommand command;

    public SerializedSimpleCommand(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
