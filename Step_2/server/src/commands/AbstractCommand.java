package commands;

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractCommand {
    public abstract void execute(Object object, Socket socket) throws IOException;
}