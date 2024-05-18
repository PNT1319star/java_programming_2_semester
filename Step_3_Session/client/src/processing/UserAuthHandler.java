package processing;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.ConnectionErrorException;
import exceptions.LoginException;
import interaction.Request;
import interaction.Response;
import interaction.User;
import utilities.UserAuthenticator;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.util.Scanner;

public class UserAuthHandler {
    private final Scanner scanner;
    private final Communicator communicator;
    private String session_id;

    public UserAuthHandler(Scanner scanner, Communicator communicator) {
        this.scanner = scanner;
        this.communicator = communicator;
    }

    private Request handleAuthentication() {
        UserAuthenticator userAuthenticator = new UserAuthenticator(scanner);
        String loginCommand = "login";
        String registerCommand = "register";
        String command = userAuthenticator.ask("Already have an account?") ? loginCommand : registerCommand;
        User user = new User(userAuthenticator.askLogin(), userAuthenticator.askPassWord());
        return new Request(command, user, "","");
    }

    public void processAuthentication() throws LoginException, ConnectionErrorException {
        Request request;
        Response response ;
        try {
            request = handleAuthentication();
            communicator.connect();
            Sender sender = new Sender(communicator.getSocketChannel().socket());
            sender.sendObject(request);
            Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
            response = receiver.receive();
            if (response != null) {
                ConsolePrinter.printResult(response.getAnswer());
                session_id = response.getAnswer();
            } else throw new LoginException();
        } catch (InvalidClassException | NotSerializableException exception) {
            ConsolePrinter.printError("An error occurred while sending data to the server!");
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("An error occurred while reading received data!");
        } catch (IOException exception) {
            ConsolePrinter.printError("The connection to the server has been lost!");
        } catch (ConnectionErrorException exception) {
            throw new ConnectionErrorException();
        }
    }

    public String getSessionId() {
        return session_id;
    }
}
