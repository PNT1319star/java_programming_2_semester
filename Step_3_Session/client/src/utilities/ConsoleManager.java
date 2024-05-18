package utilities;

import connector.Communicator;
import exceptions.ConnectionErrorException;
import exceptions.LoginException;
import interaction.User;
import mode.UserInputMode;
import processing.CommandHandler;
import processing.UserAuthHandler;
import utility.ConsolePrinter;

import java.util.Locale;
import java.util.Objects;
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
            String session_id;
            do {
                userAuthHandler.processAuthentication();
                session_id = userAuthHandler.getSessionId();
            } while (session_id.contains("Incorrect username or password!"));
            CommandHandler commandHandler = new CommandHandler(communicator, session_id);
            userInputMode.executeMode(commandHandler);
        } catch (ConnectionErrorException exception) {
            ConsolePrinter.printError("The number of connection attempts has been exceeded!");
            ConsolePrinter.printError("Can not connect to server!");
        } catch (LoginException exception) {
            ConsolePrinter.printError("Something wrong with the login!");
        }
    }
}
