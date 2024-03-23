package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * The AddIfMaxCommand class represents a command to add a new element to a collection
 * if its value is greater than the value of the largest element of this collection.
 * It extends the AbstractCommand class.
 */
public class AddIfMaxCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    public AddIfMaxCommand(Receiver receiver) {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
        this.receiver = receiver;
    }
    public AddIfMaxCommand() {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.addIfMax(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add_if_max command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
