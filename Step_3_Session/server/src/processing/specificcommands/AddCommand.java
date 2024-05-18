package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class AddCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public AddCommand(ServerCommandProcessor serverCommandProcessor) {
        super("add {element}", "add a new element to the collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.add(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
