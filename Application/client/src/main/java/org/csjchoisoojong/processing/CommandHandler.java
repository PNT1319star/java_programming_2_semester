package org.csjchoisoojong.processing;

import org.csjchoisoojong.connector.Communicator;
import org.csjchoisoojong.connector.Receiver;
import org.csjchoisoojong.connector.Sender;
import org.csjchoisoojong.exceptions.ConnectionErrorException;
import org.csjchoisoojong.interaction.Request;
import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class CommandHandler {
    private final Communicator communicator;
    private String session_id;
    private final Scanner scanner;

    public CommandHandler(Communicator communicator, Scanner scanner) {
        this.communicator = communicator;
        this.scanner = scanner;
    }

    public void processCommand(Request request) throws IOException, ClassNotFoundException, ConnectionErrorException {
        communicator.connect();
        Sender sender = new Sender(communicator.getSocketChannel().socket());
        sender.sendObject(request);
        Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
        Response response = receiver.receive();
        String answer = response.getAnswer();
        ConsolePrinter.printResult(answer);
        communicator.closeConnection();
    }

    public int executeCommand(String commandName, String argument, Serializable object) {
        try {
            Request request;
            request = new Request(commandName, object, argument, session_id);
            processCommand(request);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ConnectionErrorException exception) {
            ConsolePrinter.printError("Can not connect to server!");
        }
        return 1;
    }
    public String getSessionId() {
        return session_id;
    }
    public void setSessionId(String session_id) {
        this.session_id = session_id;
    }

}


