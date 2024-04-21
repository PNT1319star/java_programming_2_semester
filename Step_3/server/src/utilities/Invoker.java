package utilities;

import interaction.CommandRequest;
import processing.specificcommands.AbstractCommand;

import java.util.HashMap;

public class Invoker {
    private final static HashMap<String, AbstractCommand> commands = new HashMap<>();

    public static void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }

    public static HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }

    public static String executeCommand(CommandRequest commandRequest) {
        String name = commandRequest.getCommand();
        AbstractCommand command = commands.get(name);
        String argument = commandRequest.getArgument();
        Object object = commandRequest.getObject();
        return  command.execute(argument, object);
    }
}
