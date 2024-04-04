package processing.specificcommands;


import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class ClearCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public ClearCommand(ClientCommandProcessor processor) {
        super("clear", "clear collection");
        this.commandProcessor = processor;
    }

    public ClearCommand() {
        super("clear", "clear collection");
    }

    @Override
    public void execute(String[] arg) {
        try {
            commandProcessor.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
