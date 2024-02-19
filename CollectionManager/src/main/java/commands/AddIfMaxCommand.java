package commands;

import exceptions.WrongAmountOfElementsException;
import java.util.Scanner;

/**
 * The AddIfMaxCommand class represents a command to add a new element to a collection
 * if its value is greater than the value of the largest element of this collection.
 * It extends the AbstractCommand class.
 */
public class AddIfMaxCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs an AddIfMaxCommand object with the specified receiver.
     *
     * @param receiver the receiver to add the new element if it is maximum
     */
    public AddIfMaxCommand(Receiver receiver) {
        super("\u001B[36madd_if_max {element}\u001B[0m", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
        this.receiver = receiver;
    }

    /**
     * Executes the add_if_max command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.addIfMax(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add_if_max command.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
