package processing.serializedcommands;

import processing.specificcommands.AbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final AbstractCommand command;
    private final Object object;
    public SerializedCommandWithObject(AbstractCommand command, Object object) {
        this.command = command;
        this.object = object;
    }
    public AbstractCommand getCommand() {
        return command;
    }
    public Object getObject() {
        return object;
    }
}
