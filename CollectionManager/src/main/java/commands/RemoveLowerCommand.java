package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The RemoveLowerCommand class represents a command to remove all elements smaller than a given one from a collection.
 * This command removes elements from the collection that are smaller than a specified organization.
 */
public class RemoveLowerCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a RemoveLowerCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public RemoveLowerCommand(Receiver receiver) {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
        this.receiver = receiver;
    }

    /**
     * Executes the remove_lower command, removing all elements smaller than a given one from the collection.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
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
