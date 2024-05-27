package org.csjchoisoojong.processing;

import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.connector.Receiver;
import org.csjchoisoojong.connector.Sender;
import org.csjchoisoojong.controllers.MainWindowController;
import org.csjchoisoojong.exceptions.ConnectionErrorException;
import org.csjchoisoojong.exceptions.LoginException;
import org.csjchoisoojong.interaction.Request;
import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import org.csjchoisoojong.interaction.User;
import org.csjchoisoojong.utilities.UIOutputer;
import org.csjchoisoojong.utilities.UserAuthenticator;
import org.csjchoisoojong.utility.ConsolePrinter;

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

    public boolean processAuthentication(String username, String password, boolean register) {
        Request request = null;
        Response response = null;
        String command;
        try {
            command = register ? MainWindowController.REGISTER_COMMAND_NAME : MainWindowController.LOGIN_COMMAND_NAME;
            request = new Request(command, new User(username, password), "", "");
            Sender sender = new Sender(communicator.getSocketChannel().socket());
            sender.sendObject(request);
            Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
            response = receiver.receive();
            UIOutputer.tryError(response.getAnswer(), response.getArguments());
        } catch (InvalidClassException | NotSerializableException exception){
            UIOutputer.printError("DataSendingException");
        } catch (ClassNotFoundException exception) {
            UIOutputer.printError("DataReadingException");
        } catch (IOException exception) {
            UIOutputer.printError("EndConnectionToServerException");
        }
        if (response != null && response.getResponseCode().equals(ResponseCode.OK)) {
            session_id = response.getAnswer();
            return true;
        }
        return false;
    }

    public String getSessionId() {
        return session_id;
    }
}
