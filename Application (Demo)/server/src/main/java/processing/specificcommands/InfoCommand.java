package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;


public class InfoCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public InfoCommand(ServerCommandProcessor serverCommandProcessor) {
        super("info", "print information about the collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK,serverCommandProcessor.info(),null);
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command", null);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
