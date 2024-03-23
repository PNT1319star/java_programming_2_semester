package commands.serializedcommands;

import commands.AbstractCommand;

import java.io.Serializable;

public class SerializedCommandWithObjectAndArgument implements Serializable {
    private AbstractCommand command;
    private Object objectToSend;
    private String argument;


    public SerializedCommandWithObjectAndArgument(AbstractCommand command, Object objectToSend, String argument) {
        this.command = command;
        this.objectToSend = objectToSend;
        this.argument = argument;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public Object getObject() {
        return objectToSend;
    }

    public String getArgument() {
        return argument;
    }
}
