package processing.specificcommands;

import processing.ServerCommandProcessor;

public class ViewUserRoleCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public ViewUserRoleCommand(ServerCommandProcessor serverCommandProcessor) {
        super("view_role", "view all users and their roles (only for admin)");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        return serverCommandProcessor.viewRole();
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
