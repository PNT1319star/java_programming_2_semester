package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class ClearCommand extends AbstractCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            commandProcessor.clear();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
