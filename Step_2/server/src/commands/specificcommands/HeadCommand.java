package commands.specificcommands;

import commands.AbstractCommand2;
import server.Receiver;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.Socket;

public class HeadCommand extends AbstractCommand2 implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object object, Socket socket) throws IOException {
        Receiver receiver = new Receiver(socket);
        receiver.head();
    }
}
