package processing.specificcommands;

import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class PrintUniqPostalAddCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public PrintUniqPostalAddCommand(ClientCommandProcessor processor) {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
        this.commandProcessor = processor;
    }

    public PrintUniqPostalAddCommand() {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        commandProcessor.printUniquePostalAddress();

    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
