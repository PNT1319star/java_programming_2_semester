package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RoleChangeCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RoleChangeCommand(ServerCommandProcessor serverCommandProcessor) {
        super("change_role {role}", "change user's role (only for admin)");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
            return serverCommandProcessor.changeRole(argument, (String) object);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
