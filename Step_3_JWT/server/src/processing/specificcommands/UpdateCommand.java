package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class UpdateCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public UpdateCommand(ServerCommandProcessor serverCommandProcessor) {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.update(argument, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
