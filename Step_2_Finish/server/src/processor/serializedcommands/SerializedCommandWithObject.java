package processor.serializedcommands;

import processor.specificcommands.ServerAbstractCommand;

import java.io.Serializable;

public class SerializedCommandWithObject implements Serializable {
    private ServerAbstractCommand command;
    private Object object;
    public SerializedCommandWithObject(ServerAbstractCommand command, Object object) {
        this.command = command;
        this.object = object;
    }
    public ServerAbstractCommand getCommand() {
        return command;
    }
    public Object getObject() {
        return object;
    }
}
