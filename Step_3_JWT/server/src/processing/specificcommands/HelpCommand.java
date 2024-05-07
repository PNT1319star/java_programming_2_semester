package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class HelpCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public HelpCommand(ServerCommandProcessor serverCommandProcessor) {
        super("help", "display help on available commands");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.help();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}