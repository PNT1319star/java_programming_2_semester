package org.csjchoisoojong.interaction;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String command;
    private final Serializable object;
    private final String argument;
    private final String session_id;

    public Request(String command, Serializable object, String argument, String session_id) {
        this.command = command;
        this.object = object;
        this.argument = argument;
        this.session_id = session_id;
    }

    public String getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getArgument() {
        return argument;
    }

    public String getSessionId() {
        return session_id;
    }

    public boolean isEmpty() {
        return command.isEmpty() && argument.isEmpty() && object == null;
    }
}
