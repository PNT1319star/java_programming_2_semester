package utilities;

import interaction.Request;
import processing.specificcommands.AbstractCommand;

import java.util.HashMap;

public class Invoker {
    private final static HashMap<String, AbstractCommand> viewCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> editCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> adminCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> innerServerCommands = new HashMap<>();

    private final static HashMap<String, HashMap<String, AbstractCommand>> roleCommandsList = new HashMap<>();


    public static void putViewCommands(String name, AbstractCommand command) {
        viewCommands.put(name, command);
    }

    public static void putEditCommands(String name, AbstractCommand command) {
        editCommands.put(name, command);
    }

    public static void putAdminCommands(String name, AbstractCommand command) {
        adminCommands.put(name, command);
    }
    public static void putInnerServerCommands(String name, AbstractCommand command) {
        innerServerCommands.put(name, command);
    }
    public static void register() {
        roleCommandsList.put("view", viewCommands);
        roleCommandsList.put("edit", editCommands);
        roleCommandsList.put("function", adminCommands);
        roleCommandsList.put("server", innerServerCommands);
    }


    public static HashMap<String, HashMap<String, AbstractCommand>> getRoleCommandsList() {
        return roleCommandsList;
    }

    public static String executeCommand(Request commandRequest, HashMap<String, AbstractCommand> commands) {
        String name = commandRequest.getCommand();
        AbstractCommand command = commands.get(name);
        String argument = commandRequest.getArgument();
        Object object = commandRequest.getObject();
        return command.execute(argument, object);
    }
}
