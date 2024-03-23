package commands.specificcommands;

import commands.AbstractCommand;
import server.Receiver;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class RemoveLowerCommand extends AbstractCommand implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object object, Socket socket) throws IOException {
        Receiver receiver = new Receiver(socket);
        receiver.removeLower(object);
    }
}
