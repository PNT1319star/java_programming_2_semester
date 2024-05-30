
package org.csjchoisoojong.script;


import org.csjchoisoojong.exceptions.ConnectionErrorException;
import org.csjchoisoojong.exceptions.ScriptRecursionException;
import org.csjchoisoojong.exceptions.WrongAmountOfElementsException;
import org.csjchoisoojong.interaction.Request;
import org.csjchoisoojong.processing.CommandHandler;
import org.csjchoisoojong.utility.ConsolePrinter;
import org.csjchoisoojong.utility.OrganizationCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileScriptHandler {
    private int commandStatus = 0;
    private final List<File> scriptStack = new ArrayList<>();
    private Scanner userScanner;
    private final String session_id;
    private final CommandHandler commandHandler;

    public FileScriptHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.session_id = commandHandler.getSessionId();
    }

    public boolean execute(File file) {
        try {
            initializeScript(file);
            do {
                String userCommand = getNextUserCommand();
                commandStatus = handleUserCommand(userCommand);
            } while (commandStatus == 1 && userScanner.hasNextLine());
            return true;
        } catch (FileNotFoundException exception) {
            ConsolePrinter.printError("Find not found!");
        } catch (NoSuchElementException exception) {
            ConsolePrinter.printError("The script file is empty!");
        } catch (IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected error!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ScriptRecursionException e) {
            ConsolePrinter.printError("The script cannot be executed recursively");
        }
        return false;
    }

    public void initializeScript(File file) throws FileNotFoundException, NoSuchElementException {
        this.userScanner = new Scanner(file);
        if (!userScanner.hasNext()) throw new NoSuchElementException();
        scriptStack.add(file);
    }

    public String getNextUserCommand() {
        String userCommand = this.userScanner.nextLine().trim();
        ConsolePrinter.printInformation(">" + userCommand);
        return userCommand;
    }

    public int handleUserCommand(String userCommand) throws IOException, ScriptRecursionException {
        try {
            String[] commandPart = userCommand.split(" ").length > 1 ? userCommand.split(" ") : new String[]{userCommand.split(" ")[0], ""};
            String commandName = commandPart[0].trim();
            String commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
            while (this.userScanner.hasNextLine() && commandName.isEmpty()) {
                commandPart = userCommand.split(" ", 2);
                commandName = commandPart[0].trim();
                commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
            }
            if (commandName.equals("execute_script")) {
                for (File file : scriptStack) {
                    if (file.equals(new File(commandArgument))) throw new ScriptRecursionException();
                }
                Request request = executeCommand(commandPart);
                commandHandler.processCommand(request);
                return 1;
            }
            Request request = executeCommand(commandPart);
            commandHandler.processCommand(request);
            return 1;
        } catch (ConnectionErrorException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Request executeCommand(String[] commandSet) {
        try {
            Request request = null;
            switch (commandSet[0].toLowerCase()) {
                case "info", "clear":
                    if (!commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], null, "", session_id);
                    break;
                case "add", "add_if_max", "remove_lower":
                    if (!commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], OrganizationCreator.organizationCreator(userScanner), "", session_id);
                    break;
                case "exit" :
                    System.exit(0);
                    break;
                case "update" :
                    if (commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], OrganizationCreator.organizationCreator(userScanner), commandSet[1], session_id);
                    break;
                case "remove_by_id" :
                    if(commandSet[1].isEmpty()) throw new WrongAmountOfElementsException();
                    request = new Request(commandSet[0], null, commandSet[1], session_id);
                    break;
                default:
                    ConsolePrinter.printError("Command does not exist!");
            }
            return request;
        } catch (WrongAmountOfElementsException exception) {
            ConsolePrinter.printError("Invalid command format");
            return null;
        }
    }

}
