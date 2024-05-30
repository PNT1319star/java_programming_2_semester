package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

public class ShowCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public ShowCommand(ServerCommandProcessor serverCommandProcessor) {
        super("show", "print to standard output all the elements of the collection in string representation.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        return new Response(ResponseCode.OK, "Command has been successfully executed!", serverCommandProcessor.getCollection());
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
