package commands;

import exceptions.WrongAmountOfElementsException;

/**
 * The PrintUniqPostalAddCommand class represents a command to print the unique values of the postalAddress field of all elements in the collection.
 */
public class PrintUniqPostalAddCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a PrintUniqPostalAddCommand with the specified receiver.
     *
     * @param receiver the receiver to use for executing the command
     */
    public PrintUniqPostalAddCommand(Receiver receiver) {
        super("\u001B[36mprint_unique_postal_address\u001B[0m", "print the unique values of the postalAddress field of all elements in the collection.");
        this.receiver = receiver;
    }

    /**
     * Executes the command to print unique postal addresses.
     *
     * @param arg the command arguments (not used for this command)
     * @throws WrongAmountOfElementsException if the number of command arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.printUniquePostalAddress();
    }

    /**
     * Displays information about the PrintUniqPostalAddCommand.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
