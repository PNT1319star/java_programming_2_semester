package server;

import java.io.IOException;
import java.net.Socket;

public class CommandHandler {
    public void handle(Socket socket) throws IOException {
        try {
            CommandReceiver commandReceiver = new CommandReceiver(socket);
            commandReceiver.receiveAndDecode();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
