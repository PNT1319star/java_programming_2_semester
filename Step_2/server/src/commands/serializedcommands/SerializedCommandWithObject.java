package commands.serializedcommands;

import commands.AbstractCommand2;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand2 command;
    private final Object objectToSend;

    public SerializedCommandWithObject(AbstractCommand2 command, Object objectToSend) {
        this.command = command;
        this.objectToSend = objectToSend;
    }

    public AbstractCommand2 getCommand() {
        return command;
    }

    public Object getObject() {
        return objectToSend;
    }
}
