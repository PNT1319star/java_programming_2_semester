package org.csjchoisoojong.utilities;

import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.exceptions.ConnectionErrorException;
import org.csjchoisoojong.exceptions.LoginException;
import org.csjchoisoojong.processing.CommandHandler;
import org.csjchoisoojong.processing.UserAuthHandler;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.util.Scanner;

public class ConsoleManager {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    public static void interactive(String host, String sPort) {
        try {
            int port = Integer.parseInt(sPort);
            Communicator communicator = new Communicator(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
            UserAuthHandler userAuthHandler = new UserAuthHandler(new Scanner(System.in), communicator);
            String session_id;
            do {
                userAuthHandler.processAuthentication();
                session_id = userAuthHandler.getSessionId();
            } while (session_id.contains("Incorrect username or password!") || session_id.contains("User has been existed!"));
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
