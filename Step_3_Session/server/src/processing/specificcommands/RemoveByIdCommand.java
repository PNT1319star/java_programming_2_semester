package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveByIdCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RemoveByIdCommand(ServerCommandProcessor serverCommandProcessor) {
        super("remove_by_id ", "remove an element from a collection by its id");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.removeById(argument);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
