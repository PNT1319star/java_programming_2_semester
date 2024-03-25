package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * The PrintUniqPostalAddCommand class represents a command to print the unique values of the postalAddress field of all elements in the collection.
 */
public class PrintUniqPostalAddCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
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

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.printUniquePostalAddress();
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
