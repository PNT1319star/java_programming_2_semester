package commands;

public class CommandManager {
    private static Receiver receiver = new Receiver(new Invoker());

    /**
     * Starts the command execution process by registering commands with the invoker.
     */
    public static void commandStarter() {
        Invoker.register("help", new HelpCommand(receiver));
        Invoker.register("info", new InfoCommand(receiver));
        Invoker.register("show", new ShowCommand(receiver));
        Invoker.register("add", new AddCommand(receiver));
        Invoker.register("update", new UpdateCommand(receiver));
        Invoker.register("remove_by_id", new RemoveByIdCommand(receiver));
        Invoker.register("clear", new ClearCommand(receiver));
        Invoker.register("save", new SaveCommand(receiver));
        Invoker.register("execute_script", new ExecuteScriptCommand(receiver));
        Invoker.register("exit", new ExitCommand(receiver));
        Invoker.register("head", new HeadCommand(receiver));
        Invoker.register("add_if_max", new AddIfMaxCommand(receiver));
        Invoker.register("remove_lower", new RemoveLowerCommand(receiver));
        Invoker.register("min_by_creation_date", new MinByCreationDateCommand(receiver));
        Invoker.register("filter_starts_with_full_name", new FilterStartsWFullNameCommand(receiver));
        Invoker.register("print_unique_postal_address", new PrintUniqPostalAddCommand(receiver));
        Invoker.register("roll_back", new RollBackCommand(receiver));
    }
}
