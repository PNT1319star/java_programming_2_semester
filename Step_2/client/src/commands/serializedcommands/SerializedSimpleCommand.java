package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedSimpleCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand command;

    public SerializedSimpleCommand(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
