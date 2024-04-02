package processor.specificcommands;

import processor.ServerCommandProcessor;

import java.io.Serial;
import java.io.Serializable;

public class AddCommand extends ServerAbstractCommand implements Serializable {
    @Serial
    private static final long serialVersionUID = 32L;
    @Override
    public byte[] execute(Object object) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.add(object);
    }
}
