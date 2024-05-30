package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

public class RegisterCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RegisterCommand(ServerCommandProcessor serverCommandProcessor) {
        super("register", "inner command");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String string, Object object) {
        return new Response(ResponseCode.OK,serverCommandProcessor.register(object),null);
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
