package utilities;

import org.csjchoisoojong.interaction.Request;
import processing.specificcommands.AbstractCommand;

import java.util.HashMap;

public class Invoker {
    private final static HashMap<String, AbstractCommand> viewCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> insertCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> deleteCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> updateCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> adminCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> innerServerCommands = new HashMap<>();
    private final static HashMap<String, AbstractCommand> commandsList = new HashMap<>();

    private final static HashMap<String, HashMap<String, AbstractCommand>> roleCommandsList = new HashMap<>();

    public static void putInsertCommands(String name, AbstractCommand command) {
        insertCommands.put(name, command);
    }

    public static void putDeleteCommands(String name, AbstractCommand command) {
        deleteCommands.put(name, command);
    }

    public static void putUpdateCommands(String name, AbstractCommand command) {
        updateCommands.put(name, command);
    }

    public static void putViewCommands(String name, AbstractCommand command) {
        viewCommands.put(name, command);
    }

    public static void putAdminCommands(String name, AbstractCommand command) {
        adminCommands.put(name, command);
    }

    public static void putInnerServerCommands(String name, AbstractCommand command) {
        innerServerCommands.put(name, command);
    }

    public static void register() {
        roleCommandsList.put("view", viewCommands);
        roleCommandsList.put("insert", insertCommands);
        roleCommandsList.put("delete", deleteCommands);
        roleCommandsList.put("update", updateCommands);
        roleCommandsList.put("function", adminCommands);
        roleCommandsList.put("server", innerServerCommands);
        commandsList.putAll(viewCommands);
        commandsList.putAll(insertCommands);
        commandsList.putAll(deleteCommands);
        commandsList.putAll(updateCommands);
        commandsList.putAll(adminCommands);
    }


    public static HashMap<String, HashMap<String, AbstractCommand>> getRoleCommandsList() {
        return roleCommandsList;
    }

    public static HashMap<String, AbstractCommand> getCommandsList() {
        return commandsList;
    }

    public static String executeCommand(Request commandRequest, HashMap<String, AbstractCommand> commands) throws NullPointerException {
        String name = commandRequest.getCommand();
        AbstractCommand command = commands.get(name);
        String argument = commandRequest.getArgument();
        Object object = commandRequest.getObject();
        return command.execute(argument, object);
    }
}
