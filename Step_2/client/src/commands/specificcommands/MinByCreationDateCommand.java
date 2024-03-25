package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * The MinByCreationDateCommand class represents a command to print any object from the collection whose creationDate field value is minimal.
 */
public class MinByCreationDateCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a MinByCreationDateCommand with the specified receiver.
     *
     * @param receiver the receiver to use for executing the command
     */
    public MinByCreationDateCommand(Receiver receiver) {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
        this.receiver = receiver;
    }
    public MinByCreationDateCommand() {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
    }

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.minByCreationDate();
    }

    /**
     * Displays information about the MinByCreationDateCommand.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
