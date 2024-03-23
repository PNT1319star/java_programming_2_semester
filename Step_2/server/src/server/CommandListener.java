package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandListener {
    private final CommandHandler commandHandler;

    public CommandListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
    public void startListening(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread (() -> {
                    try {
                        commandHandler.handle(socket);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
