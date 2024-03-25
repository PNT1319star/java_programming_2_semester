package commands.serializedcommands;

import commands.AbstractCommand2;

import java.io.Serial;
import java.io.Serializable;

public class SerializedSimpleCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand2 command;

    public SerializedSimpleCommand(AbstractCommand2 command) {
        this.command = command;
    }

    public AbstractCommand2 getCommand() {
        return command;
    }
}
