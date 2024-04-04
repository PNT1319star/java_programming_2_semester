package processing.specificcommands;

import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

public class UpdateCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public UpdateCommand(ClientCommandProcessor processor) {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
        this.commandProcessor = processor;
    }

    public UpdateCommand() {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
    }

    @Override
    public void execute(String[] arg) {
        try {
            commandProcessor.update(arg[1], new Scanner(System.in));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
