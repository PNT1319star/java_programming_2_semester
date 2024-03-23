package client;

import commands.serializedcommands.SerializedCommandWithArgument;
import commands.serializedcommands.SerializedCommandWithObject;
import commands.serializedcommands.SerializedCommandWithObjectAndArgument;
import commands.serializedcommands.SerializedSimpleCommand;
import commands.specificcommands.*;
import utility.ConsolePrinter;
import utility.OrganizationCreator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Receiver {
    private Invoker invoker;
    private Sender sender;
    private ObjectInputStream inputStream;

    public Receiver(Invoker invoker,Sender sender, ObjectInputStream inputStream) {
        this.invoker = invoker;
        this.sender = sender;
        this.inputStream = inputStream;
    }

    public void help() {
        invoker.getCommands().forEach((name, command) -> command.getCommandInformation());
        ConsolePrinter.printResult("The 'help' command has been executed successfully!");
    }

    public void info() throws IOException {
        try {
            SerializedSimpleCommand serializedInfoCommand = new SerializedSimpleCommand(new InfoCommand());
            sender.sendObject(serializedInfoCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void show() throws IOException {
        try {
            SerializedSimpleCommand serializedShowCommand = new SerializedSimpleCommand(new ShowCommand());
            sender.sendObject(serializedShowCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void add(Scanner scanner) throws IOException {
        try {
            SerializedCommandWithObject serializedAddCommand = new SerializedCommandWithObject(new AddCommand(), OrganizationCreator.organizationCreator(scanner));
            sender.sendObject(serializedAddCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void addIfMax(Scanner scanner) throws IOException {
        try {
            SerializedCommandWithObject serializedAddIfMaxCommand = new SerializedCommandWithObject(new AddIfMaxCommand(), OrganizationCreator.organizationCreator(scanner));
            sender.sendObject(serializedAddIfMaxCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException e) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void update(String sID, Scanner scanner) throws IOException {
        try {
            SerializedCommandWithObjectAndArgument serializedUpdateCommand = new SerializedCommandWithObjectAndArgument(new UpdateCommand(), OrganizationCreator.organizationCreator(scanner), sID);
            sender.sendObject(serializedUpdateCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void removeById(String sID) throws IOException {
        try {
            SerializedCommandWithArgument serializedRemoveByIDCommand = new SerializedCommandWithArgument(new RemoveByIdCommand(), sID);
            sender.sendObject(serializedRemoveByIDCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void clear() throws IOException {
        try {
            SerializedSimpleCommand serializedClearCommand = new SerializedSimpleCommand(new ClearCommand());
            sender.sendObject(serializedClearCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void executeScript(String path) throws IOException {
        try {
            SerializedCommandWithArgument serializedExecuteScriptCommand = new SerializedCommandWithArgument(new ExecuteScriptCommand(), path);
            sender.sendObject(serializedExecuteScriptCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void exit() {
        ConsolePrinter.printResult("The 'exit' command has been executed successfully!");
        ConsolePrinter.printResult("Program has been closed!");
        System.exit(0);
    }

    public void head() throws IOException {
        try {
            SerializedSimpleCommand serializedHeadCommand = new SerializedSimpleCommand(new HeadCommand());
            sender.sendObject(serializedHeadCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void removeLower(Scanner scanner) throws IOException {
        try {
            SerializedCommandWithObject serializedRemoveLowerCommand = new SerializedCommandWithObject(new RemoveLowerCommand(), OrganizationCreator.organizationCreator(scanner));
            sender.sendObject(serializedRemoveLowerCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void minByCreationDate() throws IOException {
        try {
            SerializedSimpleCommand serializedMinByCreationDateCommand = new SerializedSimpleCommand(new MinByCreationDateCommand());
            sender.sendObject(serializedMinByCreationDateCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void filterStartsWithFullName(String fullName) throws IOException {
        try {
            SerializedCommandWithArgument serializedFilterStartsWithFullNameCommand = new SerializedCommandWithArgument(new FilterStartsWFullNameCommand(), fullName);
            sender.sendObject(serializedFilterStartsWithFullNameCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Error while processing command response!");
        }
    }

    public void printUniquePostalAddress() throws IOException {
        try {
            SerializedSimpleCommand serializedPrintUniquePostalAddressCommand = new SerializedSimpleCommand(new PrintUniqPostalAddCommand());
            sender.sendObject(serializedPrintUniquePostalAddressCommand);
            String result = (String) inputStream.readObject();
            ConsolePrinter.printResult(result);
        } catch (ClassNotFoundException exception) {
            ConsolePrinter.printError("Something went wrong in the server! The command can be executed!");
        }
    }
}
