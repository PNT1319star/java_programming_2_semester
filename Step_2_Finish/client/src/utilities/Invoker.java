package utilities;

import processing.specificcommands.ClientAbstractCommand;
import utility.ConsolePrinter;

import java.util.HashMap;

public class Invoker {
    private final HashMap<String, ClientAbstractCommand> commands = new HashMap<>();

    /**
     * Registers a command with its name in the commands map.
     *
     * @param name    the name of the command
     * @param command the command object
     */
    public void register(String name, ClientAbstractCommand command) {
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
                ClientAbstractCommand command = commands.get(name[0]);
                command.execute(name);
                return 1;
            } else {
                ConsolePrinter.printError("You have not entered the command !");
            }
        } catch (IllegalStateException | NullPointerException exception) {
            if (!name[0].isEmpty() && (!name[0].equals("execute_script"))) {
                ConsolePrinter.printError("The command " + name[0] + " does not exist! Use command 'help' to get the available command list !");
            }
        }
        return 0;
    }

    /**
     * Retrieves the map of registered commands.
     *
     * @return the map of commands
     */
    public HashMap<String, ClientAbstractCommand> getCommands() {
        return commands;
    }
}


