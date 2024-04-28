package processing;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.WrongAmountOfElementsException;
import interaction.Request;
import interaction.Response;
import mode.FileScriptMode;
import utilities.CommandType;
import utility.ConsolePrinter;
import utility.OrganizationCreator;

import java.io.IOException;
import java.util.Scanner;

public class CommandHandler {
    private final Communicator communicator;
    private Scanner scanner = new Scanner(System.in);

    public CommandHandler(Communicator communicator) {
        this.communicator = communicator;
    }

    public int executeCommand(String[] commandSet) {
        CommandType commandType = checkCommand(commandSet[0], commandSet[1]);
        try {
            if (commandType.equals(CommandType.SIMPLE)) {
                Request commandRequest = new Request(commandSet[0]);
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.OBJECT)) {
                Request commandRequest = new Request(commandSet[0], OrganizationCreator.organizationCreator(scanner));
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.ARGUMENT)) {
                Request commandRequest = new Request(commandSet[0], commandSet[1]);
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.UPDATE)) {
                Request commandRequest = new Request(commandSet[0], OrganizationCreator.organizationCreator(scanner), commandSet[1]);
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.SCRIPT)) {
                FileScriptMode fileScriptMode = new FileScriptMode(commandSet[1]);
                fileScriptMode.executeMode(this);
                return 1;
            } else{
                ConsolePrinter.printError("The command " + commandSet[0] + " does not exist! Use command 'help' to get the available command list !");
                return 0;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void processCommand(Request request) throws IOException, ClassNotFoundException {
        communicator.connect();
        Sender sender = new Sender(communicator.getSocketChannel().socket());
        sender.sendObject(request);
        Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
        Response response = receiver.receive();
        String answer = response.getAnswer();
        ConsolePrinter.printResult(answer);
        communicator.closeConnection();
    }

    private CommandType checkCommand(String command, String commandArgument) {
        try {
            switch (command.toLowerCase()) {
                case "help", "clear", "head", "info", "min_by_creation_date",
                        "print_unique_postal_address", "show":
                    if (!commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    break;
                case "remove_by_id", "filter_starts_with_full_name":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.ARGUMENT;
                case "add", "add_if_max", "remove_lower":
                    if (!commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.OBJECT;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.SCRIPT;
                case "update":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.UPDATE;
                case "exit":
                    System.exit(0);
                default:
                    return CommandType.ERROR;
            }
        } catch (WrongAmountOfElementsException e) {
            ConsolePrinter.printError("Invalid command format");
            return CommandType.ERROR;
        }
        return CommandType.SIMPLE;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

}