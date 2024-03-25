package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * The ClearCommand class represents a command to clear the collection.
 * It extends the AbstractCommand class.
 */
public class ClearCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    public ClearCommand(Receiver receiver) {
        super("clear", "clear collection");
        this.receiver = receiver;
    }

    public ClearCommand() {
        super("clear", "clear collection");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.clear();
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
