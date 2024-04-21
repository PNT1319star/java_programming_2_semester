package processing;

import processing.specificcommands.*;
import utilities.Invoker;


public class CommandManager {
    public static void invokeCommand() {
        Invoker.register("help", new HelpCommand());
        Invoker.register("info", new InfoCommand());
        Invoker.register("show", new ShowCommand());
        Invoker.register("add", new AddCommand());
        Invoker.register("update", new UpdateCommand());
        Invoker.register("remove_by_id", new RemoveByIdCommand());
        Invoker.register("clear", new ClearCommand());
        Invoker.register("head", new HeadCommand());
        Invoker.register("add_if_max", new AddIfMaxCommand());
        Invoker.register("remove_lower", new RemoveLowerCommand());
        Invoker.register("min_by_creation_date", new MinByCreationDateCommand());
        Invoker.register("filter_starts_with_full_name", new FilterStartsWFullNameCommand());
        Invoker.register("print_unique_postal_address", new PrintUniqPostalAddCommand());
    }
}
