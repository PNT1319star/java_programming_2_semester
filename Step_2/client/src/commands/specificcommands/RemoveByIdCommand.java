package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;

/**
 * The RemoveByIdCommand class represents a command to remove an element from a collection by its id.
 * This command is used to remove an organization from the collection based on its id.
 */
public class RemoveByIdCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a RemoveByIdCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public RemoveByIdCommand(Receiver receiver) {
        super("remove_by_id ", "remove an element from a collection by its id");
        this.receiver = receiver;
    }
    public RemoveByIdCommand() {
        super("remove_by_id ", "remove an element from a collection by its id");
    }

    /**
     * Executes the remove_by_id command, removing an element from the collection by its id.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws IOException {
        receiver.removeById(arg[1]);
    }

    /**
     * Displays information about the remove_by_id command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
