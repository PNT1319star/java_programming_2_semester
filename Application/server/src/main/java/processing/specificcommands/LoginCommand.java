package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

public class LoginCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public LoginCommand(ServerCommandProcessor serverCommandProcessor) {
        super("login", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        return new Response(ResponseCode.OK, serverCommandProcessor.login(object), null);
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
