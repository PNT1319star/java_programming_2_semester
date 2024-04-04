package processing.specificcommands;

import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class MinByCreationDateCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public MinByCreationDateCommand(ClientCommandProcessor processor) {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
        this.commandProcessor = processor;
    }

    public MinByCreationDateCommand() {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
    }

    @Override
    public void execute(String[] arg) {
        try {
            commandProcessor.minByCreationDate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
