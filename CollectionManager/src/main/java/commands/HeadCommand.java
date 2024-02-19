package commands;

import exceptions.WrongAmountOfElementsException;

/**
 * The HeadCommand class represents a command to print the first element of the collection.
 * It extends the AbstractCommand class.
 */
public class HeadCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a HeadCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public HeadCommand(Receiver receiver) {
        super("\u001B[36mhead\u001B[0m", "print the first element of the collection.");
        this.receiver = receiver;
    }

    /**
     * Executes the head command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.head();
    }

    /**
     * Retrieves information about the head command.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
