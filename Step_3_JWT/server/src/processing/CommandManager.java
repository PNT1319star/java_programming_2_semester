package processing;

import processing.specificcommands.*;
import utilities.Invoker;


public class CommandManager {
    public static void invokeCommand(ServerCommandProcessor serverCommandProcessor) {
        Invoker.register("help", new HelpCommand(serverCommandProcessor));
        Invoker.register("info", new InfoCommand(serverCommandProcessor));
        Invoker.register("show", new ShowCommand(serverCommandProcessor));
        Invoker.register("add", new AddCommand(serverCommandProcessor));
        Invoker.register("update", new UpdateCommand(serverCommandProcessor));
        Invoker.register("remove_by_id", new RemoveByIdCommand(serverCommandProcessor));
        Invoker.register("clear", new ClearCommand(serverCommandProcessor));
        Invoker.register("head", new HeadCommand(serverCommandProcessor));
        Invoker.register("add_if_max", new AddIfMaxCommand(serverCommandProcessor));
        Invoker.register("remove_lower", new RemoveLowerCommand(serverCommandProcessor));
        Invoker.register("min_by_creation_date", new MinByCreationDateCommand(serverCommandProcessor));
        Invoker.register("filter_starts_with_full_name", new FilterStartsWFullNameCommand(serverCommandProcessor));
        Invoker.register("print_unique_postal_address", new PrintUniqPostalAddCommand(serverCommandProcessor));
        Invoker.register("login", new LoginCommand(serverCommandProcessor));
        Invoker.register("register", new RegisterCommand(serverCommandProcessor));
    }
}
