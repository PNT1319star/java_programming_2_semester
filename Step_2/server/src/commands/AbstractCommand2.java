package commands;

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractCommand2 {
    public abstract void execute(Object object, Socket socket) throws IOException;
}