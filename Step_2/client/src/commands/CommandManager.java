package commands;

import client.Invoker;
import client.Receiver;
import commands.specificcommands.*;

public class CommandManager {
    public static void startCommand(Receiver receiver) {
        Invoker invoker = receiver.getInvoker();
        invoker.register("help", new HelpCommand(receiver));
        invoker.register("info", new InfoCommand(receiver));
        invoker.register("show", new ShowCommand(receiver));
        invoker.register("add", new AddCommand(receiver));
        invoker.register("update", new UpdateCommand(receiver));
        invoker.register("remove_by_id", new RemoveByIdCommand(receiver));
        invoker.register("clear", new ClearCommand(receiver));
        invoker.register("execute_script", new ExecuteScriptCommand(receiver));
        invoker.register("exit", new ExitCommand(receiver));
        invoker.register("head", new HeadCommand(receiver));
        invoker.register("add_if_max", new AddIfMaxCommand(receiver));
        invoker.register("remove_lower", new RemoveLowerCommand(receiver));
        invoker.register("min_by_creation_date", new MinByCreationDateCommand(receiver));
        invoker.register("filter_starts_with_full_name", new FilterStartsWFullNameCommand(receiver));
        invoker.register("print_unique_postal_address", new PrintUniqPostalAddCommand(receiver));
    }
}
