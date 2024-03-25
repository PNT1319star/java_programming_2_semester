package utility;

import client.Communicator;
import client.Invoker;
import client.Receiver;
import client.Sender;
import client.mode.UserInputMode;
import commands.CommandManager;
import exceptions.ConnectionErrorException;
import exceptions.NotInDeclaredLimitsException;

import java.io.IOException;


public class ConsoleManager {
    private static Communicator communicator;
    private static Receiver receiver;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    public static void interactive(String host, String port) throws IOException {
        try {
            while (true) {
                try {
                    communicator = new Communicator(host, Integer.parseInt(port), RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
                    communicator.startCommunication();
                    if (communicator.getSocket() != null && communicator.getSocket().isConnected()) {
                        ConsolePrinter.printResult("Connected to server successfully!");
                        ConsolePrinter.printInformation("Welcome to our application!");
                        ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
                        break;
                    }
                } catch (ConnectionErrorException exception) {
                    if (communicator.getReconnectionAttempts() >= MAX_RECONNECTION_ATTEMPTS) {
                        ConsolePrinter.printError("The number of connection attempts has been exceeded!");
                        break;
                    }
                    try {
                        Thread.sleep(RECONNECTION_TIMEOUT);
                    } catch (IllegalArgumentException | InterruptedException timeoutException) {
                        ConsolePrinter.printError("Connection timeout is out of the range of possible values!");
                        ConsolePrinter.printError("Reconnection will be made immediately!");
                    } catch (Exception timeoutException) {
                        ConsolePrinter.printError("An error occurred while trying to wait for a connection!");
                        ConsolePrinter.printError("Reconnection will be made immediately!");
                    }
                }
            }
        } catch (NumberFormatException exception) {
            ConsolePrinter.printError("Something went wrong with arguments!");
            System.exit(0);
        } catch (NotInDeclaredLimitsException exception) {
            ConsolePrinter.printError("The client cannot be started!");
        }
        Sender sender = new Sender(communicator);
        Invoker invoker = new Invoker();
        receiver = new Receiver(invoker, sender, communicator.getSocket());
        CommandManager.startCommand(receiver);
        UserInputMode userInputMode = new UserInputMode();
        userInputMode.executeMode();
        communicator.endCommunication();
    }

    public static Receiver getReceiver() {
        return receiver;
    }
}
