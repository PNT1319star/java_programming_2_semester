package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;


/**
 * The ShowCommand class represents a command to print all elements of the collection.
 * This command prints all elements of the collection in string representation to the standard output.
 */
public class ShowCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a ShowCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public ShowCommand(Receiver receiver) {
        super("show", "print to standard output all the elements of the collection in string representation.");
        this.receiver = receiver;
    }

    public ShowCommand () {
        super("show", "print to standard output all the elements of the collection in string representation.");
    }

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.show();
    }

    /**
     * Displays information about the show command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
