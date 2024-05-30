package utilities;

import org.csjchoisoojong.interaction.Request;
import org.csjchoisoojong.interaction.Response;
import processing.specificcommands.AbstractCommand;

import java.util.HashMap;

public class Invoker {
    private final static HashMap<String, AbstractCommand> commands = new HashMap<>();

    public static void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }

    public static Response executeCommand(Request request) {
        String name = request.getCommand();
        AbstractCommand command = commands.get(name);
        String argument = request.getArgument();
        Object object = request.getObject();
        return  command.execute(argument, object);
    }
}