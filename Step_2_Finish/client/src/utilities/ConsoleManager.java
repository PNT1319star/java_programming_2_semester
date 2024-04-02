package utilities;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import mode.UserInputMode;
import processing.ClientCommandProcessor;
import processing.CommandManager;
import run.Client;
import utility.ConsolePrinter;

import java.net.Socket;

public class ConsoleManager {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5*1000;
    private static Communicator communicator;
    private static ClientCommandProcessor processor;
    public static void interactive(String host, String sPort) {
        int port = Integer.parseInt(sPort);
        try {
            communicator = new Communicator(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
            communicator.connect();
            Socket socket = communicator.getSocketChannel().socket();
            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);
            Invoker invoker = new Invoker();
            processor = new ClientCommandProcessor(invoker, sender, receiver);
            CommandManager.startCommand(processor);
            UserInputMode userInputMode = new UserInputMode();
            userInputMode.executeMode();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static ClientCommandProcessor getProcessor() {
        return processor;
    }
}
