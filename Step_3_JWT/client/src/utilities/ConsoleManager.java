package utilities;

import connector.Communicator;
import interaction.User;
import mode.UserInputMode;
import processing.CommandHandler;
import processing.UserAuthHandler;
import utility.ConsolePrinter;

import java.util.Scanner;

public class ConsoleManager {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    public static void interactive(String host, String sPort) {
        try {
            int port = Integer.parseInt(sPort);
            Communicator communicator = new Communicator(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
            UserAuthHandler userAuthHandler = new UserAuthHandler(new Scanner(System.in), communicator);
            UserInputMode userInputMode = new UserInputMode();
            ConsolePrinter.printInformation("Welcome to my application!");
            ConsolePrinter.printInformation("You have to log in or register!");
            String token = userAuthHandler.processAuthentication();
            CommandHandler commandHandler = new CommandHandler(communicator, token);
            userInputMode.executeMode(commandHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
