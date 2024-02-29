package utility.mode;

import commands.Invoker;
import commands.Receiver;
import utility.Console;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The UserInputMode class represents the interactive mode for receiving user input and executing commands.
 */
public class UserInputMode {
    private static Console console = new Console(new Receiver(new Invoker()));
    private static Scanner userScanner;

    /**
     * Executes the interactive mode for receiving user input and executing commands.
     */
    public static void interactiveMode() {
        int commandStatus;
        console.invokerStarter();
        try {
            userScanner = new Scanner(System.in);
            do{
                commandStatus = Invoker.executeCommand(userScanner.nextLine().trim().toLowerCase().split(" "));
            } while (userScanner.hasNextLine() && commandStatus !=2 );
        } catch (NoSuchElementException exception) {
            System.err.println("No user input detected!");
        } catch (IllegalStateException exception) {
            System.err.println("Unexpected error!");
        }
    }

    /**
     * Retrieves the scanner used for reading user input in the interactive mode.
     *
     * @return The scanner used for reading user input.
     */
    public static Scanner getScanner() {
        return userScanner;
    }

}
