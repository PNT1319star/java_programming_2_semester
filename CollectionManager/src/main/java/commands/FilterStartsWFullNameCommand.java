package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The FilterStartsWFullNameCommand class represents a command to display elements whose fullName field value begins with a given substring.
 * It extends the AbstractCommand class.
 */
public class FilterStartsWFullNameCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a FilterStartsWFullNameCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public FilterStartsWFullNameCommand(Receiver receiver) {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
        this.receiver = receiver;
    }

    /**
     * Executes the filter_starts_with_full_name command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
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
