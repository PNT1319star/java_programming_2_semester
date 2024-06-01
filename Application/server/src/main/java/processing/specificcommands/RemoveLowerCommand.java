package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveLowerCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RemoveLowerCommand(ServerCommandProcessor serverCommandProcessor) {
        super("remove_lower {element} ", "remove from a collection all elements smaller than a given one.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK,serverCommandProcessor.removeLower(object), serverCommandProcessor.getCollection());
        } catch (IOException e) {
            return new Response(ResponseCode.ERROR, "CommandWrongException", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
