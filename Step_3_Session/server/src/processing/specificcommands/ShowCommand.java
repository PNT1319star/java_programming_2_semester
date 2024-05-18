package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class ShowCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public ShowCommand(ServerCommandProcessor serverCommandProcessor) {
        super("show", "print to standard output all the elements of the collection in string representation.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
