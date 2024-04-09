package utilities;

import connector.Communicator;
import mode.UserInputMode;
import processing.ClientCommandProcessor;
import processing.CommandManager;
import utility.ConsolePrinter;

public class ConsoleManager {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5*1000;
    private static Communicator communicator;
    private static ClientCommandProcessor processor;
    public static void interactive(String host, String sPort) {
        int port = Integer.parseInt(sPort);
        try {
            communicator = new Communicator(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
            Invoker invoker = new Invoker();
            processor = new ClientCommandProcessor(invoker, communicator);
            CommandManager.startCommand(processor);
            UserInputMode userInputMode = new UserInputMode();
            ConsolePrinter.printInformation("Welcome to my application!");
            ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
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
