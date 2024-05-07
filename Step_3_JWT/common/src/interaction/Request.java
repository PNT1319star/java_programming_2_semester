package interaction;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    private final String command;
    private final Serializable object;
    private final String argument;
    private final String jwtToken;

    public Request(String command, Serializable object, String argument, String jwtToken) {
        this.command = command;
        this.object = object;
        this.argument = argument;
        this.jwtToken = jwtToken;
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

    public String getJwtToken() {
        return jwtToken;
    }

    public boolean isEmpty() {
        return command.isEmpty() && argument.isEmpty() && object == null;
    }
}
