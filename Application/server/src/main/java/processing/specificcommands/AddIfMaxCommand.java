package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class AddIfMaxCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public AddIfMaxCommand(ServerCommandProcessor serverCommandProcessor) {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK,serverCommandProcessor.addIfMax(object), serverCommandProcessor.getCollection());
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
