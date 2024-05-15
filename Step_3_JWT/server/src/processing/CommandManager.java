package processing;

import processing.specificcommands.*;
import utilities.Invoker;


public class CommandManager {
    public static void invokeCommand(ServerCommandProcessor serverCommandProcessor) {
        Invoker.putViewCommands("help", new HelpCommand(serverCommandProcessor));
        Invoker.putViewCommands("info", new InfoCommand(serverCommandProcessor));
        Invoker.putViewCommands("show", new ShowCommand(serverCommandProcessor));
        Invoker.putViewCommands("head", new HeadCommand(serverCommandProcessor));
        Invoker.putViewCommands("min_by_creation_date", new MinByCreationDateCommand(serverCommandProcessor));
        Invoker.putViewCommands("filter_starts_with_full_name", new FilterStartsWFullNameCommand(serverCommandProcessor));
        Invoker.putViewCommands("print_unique_postal_address", new PrintUniqPostalAddCommand(serverCommandProcessor));

        Invoker.putEditCommands("add", new AddCommand(serverCommandProcessor));
        Invoker.putEditCommands("update", new UpdateCommand(serverCommandProcessor));
        Invoker.putEditCommands("remove_by_id", new RemoveByIdCommand(serverCommandProcessor));
        Invoker.putEditCommands("clear", new ClearCommand(serverCommandProcessor));
        Invoker.putEditCommands("add_if_max", new AddIfMaxCommand(serverCommandProcessor));
        Invoker.putEditCommands("remove_lower", new RemoveLowerCommand(serverCommandProcessor));

        Invoker.putInnerServerCommands("login", new LoginCommand(serverCommandProcessor));
        Invoker.putInnerServerCommands("register", new RegisterCommand(serverCommandProcessor));

        Invoker.putAdminCommands("chang_role", new RoleChangeCommand(serverCommandProcessor));

        Invoker.register();
    }
}
