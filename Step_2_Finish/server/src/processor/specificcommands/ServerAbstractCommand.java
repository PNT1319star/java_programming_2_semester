package processor.specificcommands;

import java.io.IOException;
import java.net.Socket;

public abstract class ServerAbstractCommand {
    public abstract byte[] execute(Object object);
}
