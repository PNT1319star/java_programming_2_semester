package processing.specificcommands;

import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class AddCommand extends ClientAbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public AddCommand(ClientCommandProcessor commandProcessor) {
        super("add {element}", "add a new element to the collection");
        this.commandProcessor = commandProcessor;
    }

    public AddCommand() {
        super("add {element}", "add a new element to the collection");
    }

    @Override
    public void execute(String[] arg) {
        try {
            commandProcessor.add(new Scanner(System.in));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
