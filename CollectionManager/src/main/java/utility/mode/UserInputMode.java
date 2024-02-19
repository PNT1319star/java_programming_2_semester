package utility.mode;

import commands.Invoker;
import commands.Receiver;
import utility.Console;
import utility.creator.OrganizationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInputMode {
    private static Console console = new Console(new Receiver(new Invoker()));
    private static Scanner userScanner;
    public static void interactiveMode() {
        int commandStatus;
        console.invokerStarter();
        try {
            userScanner = new Scanner(System.in);
            do{
                commandStatus = Invoker.executeCommand(userScanner.nextLine().trim().split(" "));
            } while (userScanner.hasNextLine() && commandStatus !=2 );
        } catch (NoSuchElementException exception) {
            System.err.println("No user input detected!");
        } catch (IllegalStateException exception) {
            System.err.println("Unexpected error!");
        }
    }

    public static Scanner getScanner() {
        return userScanner;
    }

}
