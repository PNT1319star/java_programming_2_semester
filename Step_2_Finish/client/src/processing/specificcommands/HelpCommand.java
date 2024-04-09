package processing.specificcommands;


import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.Serial;
import java.io.Serializable;

public class HelpCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public HelpCommand(ClientCommandProcessor commandProcessor) {
        super("help", "display help on available commands");
        this.commandProcessor = commandProcessor;
    }

    public HelpCommand() {
        super("help", "display help on available commands");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        commandProcessor.help();
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
