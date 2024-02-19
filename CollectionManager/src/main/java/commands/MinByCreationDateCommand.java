package commands;

import exceptions.WrongAmountOfElementsException;

/**
 * The MinByCreationDateCommand class represents a command to print any object from the collection whose creationDate field value is minimal.
 */
public class MinByCreationDateCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a MinByCreationDateCommand with the specified receiver.
     *
     * @param receiver the receiver to use for executing the command
     */
    public MinByCreationDateCommand(Receiver receiver) {
        super("\u001B[36mmin_by_creation_date\u001B[0m", "print any object from the collection whose creationDate field value is minimal.");
        this.receiver = receiver;
    }

    /**
     * Executes the command to print an object with the minimal creationDate field value.
     *
     * @param arg the command arguments (not used for this command)
     * @throws WrongAmountOfElementsException if the number of command arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.minByCreationDate();
    }

    /**
     * Displays information about the MinByCreationDateCommand.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
