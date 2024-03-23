package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.Serializable;

/**
 * The FilterStartsWFullNameCommand class represents a command to display elements whose fullName field value begins with a given substring.
 * It extends the AbstractCommand class.
 */
public class FilterStartsWFullNameCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a FilterStartsWFullNameCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public FilterStartsWFullNameCommand(Receiver receiver) {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
        this.receiver = receiver;
    }
    public FilterStartsWFullNameCommand() {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
    }

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.filterStartsWithFullName(arg[1]);
    }

    /**
     * Retrieves information about the filter_starts_with_full_name command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
