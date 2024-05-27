package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class HeadCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public HeadCommand(ServerCommandProcessor serverCommandProcessor) {
        super("head", "print the first element of the collection.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.head();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
