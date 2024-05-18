package processing.specificcommands;

import processing.ServerCommandProcessor;

public class RegisterCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RegisterCommand(ServerCommandProcessor serverCommandProcessor) {
        super("register", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String string, Object object) {
        return serverCommandProcessor.register(object);
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
