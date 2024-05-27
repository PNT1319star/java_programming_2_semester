package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveLowerCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RemoveLowerCommand(ServerCommandProcessor serverCommandProcessor) {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.removeLower(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
