package processing.specificcommands;

import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class RemoveLowerCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public RemoveLowerCommand(ClientCommandProcessor processor) {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
        this.commandProcessor = processor;
    }

    public RemoveLowerCommand() {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        commandProcessor.removeLower(new Scanner(System.in));
    }

    @Override
    public void getCommandInformation() {

    }
}
