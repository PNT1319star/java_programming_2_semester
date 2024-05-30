package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class MinByCreationDateCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public MinByCreationDateCommand(ServerCommandProcessor serverCommandProcessor) {
        super("min_by_creation_date", "print any object from the collection whose creationDate field value is minimal.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, "Command has been successfully executed!", serverCommandProcessor.minByCreationDate());
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command!", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
