package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class LoginCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public LoginCommand(ServerCommandProcessor serverCommandProcessor) {
        super("login", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, serverCommandProcessor.login(object), null);
        } catch (IOException e) {
            return new Response(ResponseCode.ERROR, "IncorrectUserException", null);
        }
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
