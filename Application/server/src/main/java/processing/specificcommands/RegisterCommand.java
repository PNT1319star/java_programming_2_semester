package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class RegisterCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RegisterCommand(ServerCommandProcessor serverCommandProcessor) {
        super("register", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String string, Object object) {
        try {
            return new Response(ResponseCode.OK, serverCommandProcessor.register(object), null);
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "UserExistException", null);
        }
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
