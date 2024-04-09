package processing.specificcommands;

import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class InfoCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public InfoCommand(ClientCommandProcessor commandProcessor) {
        super("info", "print information about the collection");
        this.commandProcessor = commandProcessor;
    }

    public InfoCommand() {
        super("info", "print information about the collection");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        commandProcessor.info();
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
