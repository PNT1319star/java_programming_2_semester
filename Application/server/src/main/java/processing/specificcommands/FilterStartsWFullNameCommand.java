package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class FilterStartsWFullNameCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public FilterStartsWFullNameCommand(ServerCommandProcessor serverCommandProcessor) {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK,"Command has been successfully executed!",serverCommandProcessor.filterStartsWithFullName(argument));
        } catch (IOException exception) {
            return new Response(ResponseCode.ERROR, "Something went wrong with this command", serverCommandProcessor.getCollection());
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
