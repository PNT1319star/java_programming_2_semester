package processing.specificcommands;

import processing.ServerCommandProcessor;
import java.io.IOException;

public class MinByCreationDateCommand extends AbstractCommand {
    public MinByCreationDateCommand() {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
    }
    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.minByCreationDate();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
