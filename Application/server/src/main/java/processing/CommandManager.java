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

        Invoker.putInsertCommands("add", new AddCommand(serverCommandProcessor));
        Invoker.putInsertCommands("add_if_max", new AddIfMaxCommand(serverCommandProcessor));

        Invoker.putUpdateCommands("update", new UpdateCommand(serverCommandProcessor));

        Invoker.putDeleteCommands("remove_by_id", new RemoveByIdCommand(serverCommandProcessor));
        Invoker.putDeleteCommands("clear", new ClearCommand(serverCommandProcessor));
        Invoker.putDeleteCommands("remove_lower", new RemoveLowerCommand(serverCommandProcessor));

        Invoker.putInnerServerCommands("login", new LoginCommand(serverCommandProcessor));
        Invoker.putInnerServerCommands("register", new RegisterCommand(serverCommandProcessor));

        Invoker.putAdminCommands("change_role", new RoleChangeCommand(serverCommandProcessor));
        Invoker.putAdminCommands("view_role", new ViewUserRoleCommand(serverCommandProcessor));
        Invoker.putAdminCommands("add_function", new AddFunctionCommand(serverCommandProcessor));
        Invoker.putAdminCommands("remove_function", new RemoveFunctionCommand(serverCommandProcessor));


        Invoker.register();
    }
}
