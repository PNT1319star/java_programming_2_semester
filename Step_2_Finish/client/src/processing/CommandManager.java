package processing;

import processing.specificcommands.*;
import utilities.Invoker;

public class CommandManager {
    public static void startCommand(ClientCommandProcessor processor) {
        Invoker invoker = processor.getInvoker();
        invoker.register("help", new HelpCommand(processor));
        invoker.register("info", new InfoCommand(processor));
        invoker.register("show", new ShowCommand(processor));
        invoker.register("add", new AddCommand(processor));
        invoker.register("update", new UpdateCommand(processor));
        invoker.register("remove_by_id", new RemoveByIdCommand(processor));
        invoker.register("clear", new ClearCommand(processor));
        invoker.register("execute_script", new ExecuteScriptCommand(processor));
        invoker.register("exit", new ExitCommand(processor));
        invoker.register("head", new HeadCommand(processor));
        invoker.register("add_if_max", new AddIfMaxCommand(processor));
        invoker.register("remove_lower", new RemoveLowerCommand(processor));
        invoker.register("min_by_creation_date", new MinByCreationDateCommand(processor));
        invoker.register("filter_starts_with_full_name", new FilterStartsWFullNameCommand(processor));
        invoker.register("print_unique_postal_address", new PrintUniqPostalAddCommand(processor));
    }
}

