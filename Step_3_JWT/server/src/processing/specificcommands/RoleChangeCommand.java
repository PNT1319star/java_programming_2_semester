package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RoleChangeCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RoleChangeCommand(ServerCommandProcessor serverCommandProcessor) {
        super("change_role {role}", "change user's role");
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
