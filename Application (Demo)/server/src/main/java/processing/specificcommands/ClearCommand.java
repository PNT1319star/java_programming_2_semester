package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class ClearCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public ClearCommand(ServerCommandProcessor serverCommandProcessor) {
        super("clear", "clear collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, serverCommandProcessor.clear(), serverCommandProcessor.getCollection());
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
