package processing;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.ConnectionErrorException;
import exceptions.WrongAmountOfElementsException;
import interaction.Request;
import interaction.Response;
import mode.FileScriptMode;
import utility.ConsolePrinter;
import utility.OrganizationCreator;

import java.io.IOException;
import java.util.Scanner;

public class CommandHandler {
    private final Communicator communicator;
    private final String session_id;
    private Scanner scanner = new Scanner(System.in);

    public CommandHandler(Communicator communicator, String session_id) {
        this.communicator = communicator;
        this.session_id = session_id;
    }

    private void processCommand(Request request) throws IOException, ClassNotFoundException {
        Sender sender = new Sender(communicator.getSocketChannel().socket());
        sender.sendObject(request);
        Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
        Response response = receiver.receive();
        String answer = response.getAnswer();
        ConsolePrinter.printResult(answer);
        communicator.closeConnection();
    }

    public int executeCommand(String[] commandSet) {
        try {
            Request request = null;
            switch (commandSet[0].toLowerCase()) {
                case "help", "clear", "head", "info", "min_by_creation_date",
                        "print_unique_postal_address", "show":
                    if (!commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], null, "", session_id);
                    break;
                case "remove_by_id", "filter_starts_with_full_name":
                    if (commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], null, commandSet[1], session_id);
                    break;
                case "add", "add_if_max", "remove_lower":
                    if (!commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], OrganizationCreator.organizationCreator(scanner), "", session_id);
                    break;
                case "execute_script":
                    if (commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    FileScriptMode fileScriptMode = new FileScriptMode(commandSet[1]);
                    fileScriptMode.executeMode(this);
                case "update":
                    if (commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], OrganizationCreator.organizationCreator(scanner), commandSet[1], session_id);
                    break;
                case "change_role":
                    if (commandSet[1].isEmpty() || commandSet[2].isEmpty())
                        throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], commandSet[2], commandSet[1], session_id);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    ConsolePrinter.printError("Command does not exist!");
                    return 0;

            }
            communicator.connect();
            processCommand(request);
        } catch (WrongAmountOfElementsException e) {
            ConsolePrinter.printError("Invalid command format");
            return 0;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ConnectionErrorException exception) {
            ConsolePrinter.printError("Can not connect to server!");
        }
        return 1;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

}


