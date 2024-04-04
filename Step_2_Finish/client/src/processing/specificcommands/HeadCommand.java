package processing.specificcommands;

import processing.ClientCommandProcessor;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class HeadCommand extends AbstractCommand implements Serializable {
    transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;

    public HeadCommand(ClientCommandProcessor processor) {
        super("head", "print the first element of the collection.");
        this.commandProcessor = processor;
    }

    public HeadCommand() {
        super("head", "print the first element of the collection.");
    }

    @Override
    public void execute(String[] arg) {
        try {
            commandProcessor.head();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getCommandInformation() {

    }
}
