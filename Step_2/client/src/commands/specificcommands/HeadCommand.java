package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * The HeadCommand class represents a command to print the first element of the collection.
 * It extends the AbstractCommand class.
 */
public class HeadCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    public HeadCommand(Receiver receiver) {
        super("head", "print the first element of the collection.");
        this.receiver = receiver;
    }
    public HeadCommand() {
        super("head", "print the first element of the collection.");
    }

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.head();
    }

    /**
     * Retrieves information about the head command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
