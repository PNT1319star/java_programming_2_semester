package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class ClearCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public ClearCommand(ServerCommandProcessor serverCommandProcessor) {
        super("clear", "clear collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.clear();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
