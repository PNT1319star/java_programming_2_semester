package client;

import commands.AbstractCommand;
import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.io.IOException;
import java.util.HashMap;

public class Invoker {
    private final HashMap<String, AbstractCommand> commands = new HashMap<>();

    /**
     * Registers a command with its name in the commands map.
     *
     * @param name    the name of the command
     * @param command the command object
     */
    public void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }

    /**
     * Executes a command based on its name.
     *
     * @param name the name of the command to execute
     * @return 1 if the command is executed successfully, 0 otherwise
     */
    public int executeCommand(String[] name) {
        try {
            if (name.length > 0) {
                AbstractCommand command = commands.get(name[0]);
                command.execute(name);
                return 1;
            } else {
                ConsolePrinter.printError("You have not entered the command !");
            }
        } catch (IllegalStateException | NullPointerException exception) {
            if (!name[0].isEmpty() && (!name[0].equals("execute_script"))) {
                ConsolePrinter.printError("The command " + name[0] + " does not exist! Use command 'help' to get the available command list !");
            }
        } catch (IOException | ClassNotFoundException | WrongAmountOfElementsException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Retrieves the map of registered commands.
     *
     * @return the map of commands
     */
    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
}


