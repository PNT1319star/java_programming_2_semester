package processing;

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
import java.util.Scanner;

public class ClientCommandProcessor {
    private final Invoker invoker;
    private final Sender sender;
    private final Receiver receiver;

    public ClientCommandProcessor(Invoker invoker, Sender sender, Receiver receiver) {
        this.invoker = invoker;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void add(Scanner scanner) throws IOException {
        SerializedCommandWithObject serializedAddCommand = new SerializedCommandWithObject(new AddCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedAddCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'add' command has been executed successfully!");
    }

    public void help() {
        invoker.getCommands().forEach((name, command) -> command.getCommandInformation());
        ConsolePrinter.printResult("The 'help' command has been executed successfully!");
    }

    public void addIfMax(Scanner scanner) throws IOException {
        SerializedCommandWithObject serializedAddIfMaxCommand = new SerializedCommandWithObject(new AddIfMaxCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedAddIfMaxCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'add_if_max' command has been executed successfully!");
    }

    public void info() throws IOException {
        SerializedSimpleCommand serializedInfoCommand = new SerializedSimpleCommand(new InfoCommand());
        sender.sendObject(serializedInfoCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'info' command has been executed successfully!");
    }

    public void clear() throws IOException {
        SerializedSimpleCommand serializedClearCommand = new SerializedSimpleCommand(new ClearCommand());
        sender.sendObject(serializedClearCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'clear' command has been executed successfully!");
    }

    public void filterStartsWithFullName(String fullName) throws IOException {
        SerializedCommandWithArgument serializedFilterCommand = new SerializedCommandWithArgument(new FilterStartsWFullNameCommand(), fullName);
        sender.sendObject(serializedFilterCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'filter_starts_with_full_name' command has been executed successfully!");
    }

    public void head() throws IOException {
        SerializedSimpleCommand serializedHeadCommand = new SerializedSimpleCommand(new HeadCommand());
        sender.sendObject(serializedHeadCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'head' command has been executed successfully!");
    }

    public void minByCreationDate() throws IOException {
        SerializedSimpleCommand serializedMinByCreationDateCommand = new SerializedSimpleCommand(new MinByCreationDateCommand());
        sender.sendObject(serializedMinByCreationDateCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'min_by_creation_date' command has been executed successfully!");
    }

    public void show() throws IOException {
        SerializedSimpleCommand serializedShowCommand = new SerializedSimpleCommand(new ShowCommand());
        sender.sendObject(serializedShowCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'show' command has been executed successfully!");
    }

    public void removeLower(Scanner scanner) throws IOException {
        SerializedCommandWithObject serializedRemoveLowerCommand = new SerializedCommandWithObject(new RemoveLowerCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedRemoveLowerCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'remove_lower' command has been executed successfully!");
    }

    public void exit() {
        ConsolePrinter.printResult("The 'exit' command has been executed successfully!");
        ConsolePrinter.printResult("Program has been closed!");
        System.exit(0);
    }

    public void removeById(String sID) throws IOException {
        SerializedCommandWithArgument serializedRemoveByIdCommand = new SerializedCommandWithArgument(new RemoveByIdCommand(), sID);
        sender.sendObject(serializedRemoveByIdCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'remove_by_id' command has been executed successfully!");
    }

    public void printUniquePostalAddress() throws IOException {
        SerializedSimpleCommand serializedUniquePostalCommand = new SerializedSimpleCommand(new PrintUniqPostalAddCommand());
        sender.sendObject(serializedUniquePostalCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'print_unique_postal_address' command has been executed successfully!");
    }

    public void update(String sID, Scanner scanner) throws IOException {
        SerializedCommandWithObjectAndArgument serializedUpdateCommand = new SerializedCommandWithObjectAndArgument(new UpdateCommand(), OrganizationCreator.organizationCreator(scanner), sID);
        sender.sendObject(serializedUpdateCommand);
        byte[] data = receiver.receive();
        String result = new String(data);
        ConsolePrinter.printResult(result);
        ConsolePrinter.printResult("The 'update' command has been executed successfully!");
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
