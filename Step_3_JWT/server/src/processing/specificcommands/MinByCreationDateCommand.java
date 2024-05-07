package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class MinByCreationDateCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public MinByCreationDateCommand(ServerCommandProcessor serverCommandProcessor) {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.minByCreationDate();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
