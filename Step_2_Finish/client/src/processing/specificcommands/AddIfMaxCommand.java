package processing.specificcommands;

import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class AddIfMaxCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public AddIfMaxCommand(ClientCommandProcessor commandProcessor) {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
        this.commandProcessor = commandProcessor;
    }

    public AddIfMaxCommand() {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
    }

    @Override
    public void execute(String[] arg) throws IOException, WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        commandProcessor.addIfMax(new Scanner(System.in));
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
