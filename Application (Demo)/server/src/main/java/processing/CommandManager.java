package processing;

import processing.specificcommands.*;
import utilities.Invoker;


public class CommandManager {
    public static void invokeCommand(ServerCommandProcessor serverCommandProcessor) {
        Invoker.register("info", new InfoCommand(serverCommandProcessor));
        Invoker.register("add", new AddCommand(serverCommandProcessor));
        Invoker.register("update", new UpdateCommand(serverCommandProcessor));
        Invoker.register("remove_by_id", new RemoveByIdCommand(serverCommandProcessor));
        Invoker.register("clear", new ClearCommand(serverCommandProcessor));
        Invoker.register("add_if_max", new AddIfMaxCommand(serverCommandProcessor));
        Invoker.register("remove_lower", new RemoveLowerCommand(serverCommandProcessor));
        Invoker.register("login", new LoginCommand(serverCommandProcessor));
        Invoker.register("register", new RegisterCommand(serverCommandProcessor));
        Invoker.register("refresh", new RefreshCommand(serverCommandProcessor));
    }
}
