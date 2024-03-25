package client.mode;

import client.Invoker;
import client.Receiver;
import utility.ConsoleManager;
import utility.ConsolePrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInputMode implements IMode {
    private static Scanner userScanner;
    private static Receiver receiver = ConsoleManager.getReceiver();

    /**
     * Executes the interactive mode for receiving user input and executing commands.
     */
    @Override
    public void executeMode() {
        int commandStatus;
        try {
            userScanner = new Scanner(System.in);
            do{
                commandStatus = receiver.getInvoker().executeCommand(userScanner.nextLine().trim().toLowerCase().split(" "));
            } while (userScanner.hasNextLine() && commandStatus !=2 );
        } catch (NoSuchElementException exception) {
            ConsolePrinter.printError("No user input detected!");
        } catch (IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected error!");
        }
    }

    /**
     * Retrieves the scanner used for reading user input in the interactive mode.
     *
     * @return The scanner used for reading user input.
     */
    @Override
    public  Scanner getScanner() {
        return userScanner;
    }

}
