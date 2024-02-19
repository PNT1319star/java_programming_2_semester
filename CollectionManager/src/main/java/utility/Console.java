package utility;

import commands.*;

/**
 * The {@code Console} class represents a utility for managing commands and interacting with the program via the command line.
 * It initializes the invoker with registered commands and handles user input.
 */
public class Console {
    private Receiver receiver;

    /**
     * Constructs a console with the specified receiver.
     *
     * @param receiver the receiver object to execute commands
     */
    public Console(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Initializes the invoker with registered commands.
     * Registers all available commands with the invoker.
     */
    public void invokerStarter() {
        CollectionManager.initializationCollection();
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
    }
}
