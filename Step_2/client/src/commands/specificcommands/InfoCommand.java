package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;

public class InfoCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    /**
     * Constructs an InfoCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public InfoCommand(Receiver receiver) {
        super("info", "print information about the collection");
        this.receiver = receiver;
    }
    public InfoCommand() {
        super("info", "print information about the collection");
    }

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.info();
    }

    /**
     * Retrieves information about the info command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
