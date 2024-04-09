package processing.specificcommands;


import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class ShowCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public ShowCommand(ClientCommandProcessor processor) {
        super("show", "print to standard output all the elements of the collection in string representation.");
        this.commandProcessor = processor;
    }

    public ShowCommand() {
        super("show", "print to standard output all the elements of the collection in string representation.");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        commandProcessor.show();
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
