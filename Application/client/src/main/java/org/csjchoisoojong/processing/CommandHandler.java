package org.csjchoisoojong.processing;

import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.connector.Receiver;
import org.csjchoisoojong.connector.Sender;
import org.csjchoisoojong.data.Organization;
import org.csjchoisoojong.exceptions.ConnectionErrorException;
import org.csjchoisoojong.interaction.Request;
import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.utilities.UIOutputer;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CommandHandler {
    private final Communicator communicator;
    private String session_id;
    private final Scanner scanner;

    public CommandHandler(Communicator communicator, Scanner scanner) {
        this.communicator = communicator;
        this.scanner = scanner;
    }

    public ArrayDeque<Organization> processCommand(Request request) throws IOException, ClassNotFoundException, ConnectionErrorException {
        Sender sender = new Sender(communicator.getSocketChannel().socket());
        sender.sendObject(request);
        Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
        Response response = receiver.receive();
        String answer = response.getAnswer();
        UIOutputer.tryError(answer);
        return response.getOrganizations();
    }

    public ArrayDeque<Organization> executeCommand(String commandName, String argument, Serializable object) {
        ArrayDeque<Organization> organizations = new ArrayDeque<>();
        try {
            Request request;
            request = new Request(commandName, object, argument, session_id);
            communicator.connect();
            organizations = processCommand(request);
            communicator.closeConnection();
            return organizations;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ConnectionErrorException exception) {
            ConsolePrinter.printError("Can not connect to server!");
        }
        return organizations;
    }
    public void setSessionId(String session_id) {
        this.session_id = session_id;
    }

}


