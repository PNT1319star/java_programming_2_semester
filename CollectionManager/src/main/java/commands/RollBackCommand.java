package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The RollBackCommand class represents a command to undo a change made.
 */
public class RollBackCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Creates a new instance of the RollBackCommand class.
     *
     * @param receiver the Receiver object that will handle the rollback operation.
     */
    public RollBackCommand (Receiver receiver) {
        super("roll_back", "undo the change mades.");
        this.receiver = receiver;
    }

    /**
     * Executes the rollback command.
     *
     * @param arg the arguments for the command.
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect.
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        receiver.rollBack(arg[1]);
    }

    /**
     * Prints the command information.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
