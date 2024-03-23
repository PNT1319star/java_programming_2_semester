package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serializable;

public class SerializedCommandWithObject implements Serializable {
    private AbstractCommand command;
    private Object objectToSend;

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
