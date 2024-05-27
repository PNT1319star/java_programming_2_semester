package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class InfoCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public InfoCommand(ServerCommandProcessor serverCommandProcessor) {
        super("info", "print information about the collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.info();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
