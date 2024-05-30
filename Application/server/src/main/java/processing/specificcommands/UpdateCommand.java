package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class UpdateCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public UpdateCommand(ServerCommandProcessor serverCommandProcessor) {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, serverCommandProcessor.update(argument, object), serverCommandProcessor.getCollection());
        } catch (IOException e) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command!", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
