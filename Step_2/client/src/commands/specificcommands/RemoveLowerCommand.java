package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

/**
 * The RemoveLowerCommand class represents a command to remove all elements smaller than a given one from a collection.
 * This command removes elements from the collection that are smaller than a specified organization.
 */
public class RemoveLowerCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a RemoveLowerCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public RemoveLowerCommand(Receiver receiver) {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
        this.receiver = receiver;
    }
    public RemoveLowerCommand() {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
    }

    /**
     * Executes the remove_lower command, removing all elements smaller than a given one from the collection.
     *
     * @param arg the command arguments
     */
    @Override
    public void execute(String[] arg) throws IOException {
        receiver.removeLower(new Scanner(System.in));
    }

    /**
     * Displays information about the remove_lower command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
