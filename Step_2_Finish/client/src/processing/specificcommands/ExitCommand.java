package processing.specificcommands;

import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.Serial;
import java.io.Serializable;

public class ExitCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public ExitCommand(ClientCommandProcessor processor) {
        super("exit", "end the program (without saving to a file).");
        this.commandProcessor = processor;
    }

    public ExitCommand() {
        super("exit", "end the program (without saving to a file).");
    }

    @Override
    public void execute(String[] arg) {
        commandProcessor.exit();
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
