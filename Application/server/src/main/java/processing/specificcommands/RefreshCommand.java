package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import processing.ServerCommandProcessor;

public class RefreshCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RefreshCommand(ServerCommandProcessor serverCommandProcessor) {
        super("refresh", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String string, Object object) {
        return null;
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
