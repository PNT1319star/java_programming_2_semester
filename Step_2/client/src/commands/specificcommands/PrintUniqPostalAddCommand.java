package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;

/**
 * The PrintUniqPostalAddCommand class represents a command to print the unique values of the postalAddress field of all elements in the collection.
 */
public class PrintUniqPostalAddCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a PrintUniqPostalAddCommand with the specified receiver.
     *
     * @param receiver the receiver to use for executing the command
     */
    public PrintUniqPostalAddCommand(Receiver receiver) {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
        this.receiver = receiver;
    }
    public PrintUniqPostalAddCommand() {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
    }

    /**
     * Executes the command to print unique postal addresses.
     *
     * @param arg the command arguments (not used for this command)
     * @throws WrongAmountOfElementsException if the number of command arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws IOException {
        receiver.printUniquePostalAddress();
    }

    /**
     * Displays information about the PrintUniqPostalAddCommand.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
