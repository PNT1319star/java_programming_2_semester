package processing.specificcommands;

import processing.ServerCommandProcessor;

public class LoginCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public LoginCommand(ServerCommandProcessor serverCommandProcessor) {
        super("login", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        return serverCommandProcessor.login(object);
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
