package processing.specificcommands;

import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class FilterStartsWFullNameCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;
    public FilterStartsWFullNameCommand(ClientCommandProcessor processor) {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
        this.commandProcessor = processor;
    }
    public FilterStartsWFullNameCommand() {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
    }

    @Override
    public void execute(String[] arg) {
        try {
            commandProcessor.filterStartsWithFullName(arg[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
