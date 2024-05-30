package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class HeadCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public HeadCommand(ServerCommandProcessor serverCommandProcessor) {
        super("head", "print the first element of the collection.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK,"Command has been successfully executed!", serverCommandProcessor.head());
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
