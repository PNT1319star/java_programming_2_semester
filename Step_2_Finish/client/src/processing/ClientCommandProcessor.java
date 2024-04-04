package processing;

import connector.Communicator;
import mode.FileScriptMode;
import processing.serializedcommands.SerializedCommandWithArgument;
import processing.serializedcommands.SerializedCommandWithObject;
import processing.serializedcommands.SerializedCommandWithObjectAndArgument;
import processing.serializedcommands.SerializedSimpleCommand;
import processing.specificcommands.*;
import connector.Receiver;
import connector.Sender;
import utilities.Invoker;
import utility.ConsolePrinter;
import utility.OrganizationCreator;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientCommandProcessor {
    private final Invoker invoker;
    private final Communicator communicator;

    public ClientCommandProcessor(Invoker invoker, Communicator communicator) {
        this.invoker = invoker;
        this.communicator = communicator;
    }

    public void add(Scanner scanner) throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithObject serializedAddCommand = new SerializedCommandWithObject(new AddCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedAddCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'add' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void help() {
        invoker.getCommands().forEach((name, command) -> command.getCommandInformation());
        ConsolePrinter.printResult("The 'help' command has been executed successfully!");
    }

    public void addIfMax(Scanner scanner) throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithObject serializedAddIfMaxCommand = new SerializedCommandWithObject(new AddIfMaxCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedAddIfMaxCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'add_if_max' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void info() throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedSimpleCommand serializedInfoCommand = new SerializedSimpleCommand(new InfoCommand());
        sender.sendObject(serializedInfoCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'info' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void clear() throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedSimpleCommand serializedClearCommand = new SerializedSimpleCommand(new ClearCommand());
        sender.sendObject(serializedClearCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'clear' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void filterStartsWithFullName(String fullName) throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithArgument serializedFilterCommand = new SerializedCommandWithArgument(new FilterStartsWFullNameCommand(), fullName);
        sender.sendObject(serializedFilterCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'filter_starts_with_full_name' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void head() throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedSimpleCommand serializedHeadCommand = new SerializedSimpleCommand(new HeadCommand());
        sender.sendObject(serializedHeadCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'head' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void minByCreationDate() throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedSimpleCommand serializedMinByCreationDateCommand = new SerializedSimpleCommand(new MinByCreationDateCommand());
        sender.sendObject(serializedMinByCreationDateCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'min_by_creation_date' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void show() throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedSimpleCommand serializedShowCommand = new SerializedSimpleCommand(new ShowCommand());
        sender.sendObject(serializedShowCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'show' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void removeLower(Scanner scanner) throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithObject serializedRemoveLowerCommand = new SerializedCommandWithObject(new RemoveLowerCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedRemoveLowerCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'remove_lower' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void exit() {
        ConsolePrinter.printResult("The 'exit' command has been executed successfully!");
        ConsolePrinter.printResult("Program has been closed!");
        System.exit(0);
    }

    public void removeById(String sID) throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithArgument serializedRemoveByIdCommand = new SerializedCommandWithArgument(new RemoveByIdCommand(), sID);
        sender.sendObject(serializedRemoveByIdCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'remove_by_id' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void printUniquePostalAddress() throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedSimpleCommand serializedUniquePostalCommand = new SerializedSimpleCommand(new PrintUniqPostalAddCommand());
        sender.sendObject(serializedUniquePostalCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'print_unique_postal_address' command has been executed successfully!");
        communicator.closeConnection();
    }

    public void update(String sID, Scanner scanner) throws IOException {
        communicator.connect();
        Socket socket = communicator.getSocketChannel().socket();
        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);
        SerializedCommandWithObjectAndArgument serializedUpdateCommand = new SerializedCommandWithObjectAndArgument(new UpdateCommand(), OrganizationCreator.organizationCreator(scanner), sID);
        sender.sendObject(serializedUpdateCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'update' command has been executed successfully!");
        communicator.closeConnection();
    }
    public void executeScript(String path) throws IOException {
        FileScriptMode fileScriptMode = new FileScriptMode(path);
        fileScriptMode.executeMode();
        ConsolePrinter.printResult("The 'execute_script' command has been executed successfully!");
    }

    public Invoker getInvoker() {
        return invoker;
    }
}
