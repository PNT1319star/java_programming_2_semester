package processing.serializedcommands;

import processing.specificcommands.ClientAbstractCommand;

import java.io.Serial;
import java.io.Serializable;

public class SerializedCommandWithObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final ClientAbstractCommand command;
    private final Object objectToSend;
    public SerializedCommandWithObject(ClientAbstractCommand command, Object object){
        this.command = command;
        this.objectToSend = object;
    }
    public ClientAbstractCommand getCommand() {
        return command;
    }
    public Object getObjectToSend() {
        return objectToSend;
    }
}
