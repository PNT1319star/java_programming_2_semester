package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveByIdCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public RemoveByIdCommand(ServerCommandProcessor serverCommandProcessor) {
        super("remove_by_id ", "remove an element from a collection by its id");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, serverCommandProcessor.removeById(argument), serverCommandProcessor.getCollection());
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
