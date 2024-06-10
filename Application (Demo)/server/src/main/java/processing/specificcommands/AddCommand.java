package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class AddCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public AddCommand(ServerCommandProcessor serverCommandProcessor) {
        super("add {element}", "add a new element to the collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, serverCommandProcessor.add(object), serverCommandProcessor.getCollection());
        } catch (IOException e) {
            return new Response(ResponseCode.ERROR, "CommandWrongException", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
