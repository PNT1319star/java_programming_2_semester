package processing.specificcommands;

import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class RemoveByIdCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public RemoveByIdCommand(ClientCommandProcessor processor) {
        super("remove_by_id ", "remove an element from a collection by its id");
        this.commandProcessor = processor;
    }

    public RemoveByIdCommand() {
        super("remove_by_id ", "remove an element from a collection by its id");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        commandProcessor.removeById(arg[1]);
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
