package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand command;
    private final Object objectToSend;

    public SerializedCommandWithObject(AbstractCommand command, Object objectToSend) {
        this.command = command;
        this.objectToSend = objectToSend;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public Object getObject() {
        return objectToSend;
    }
}
