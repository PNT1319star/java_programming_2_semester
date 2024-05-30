package processing.specificcommands;

import org.csjchoisoojong.interaction.Response;
import org.csjchoisoojong.interaction.ResponseCode;
import processing.ServerCommandProcessor;

import java.io.IOException;

public class PrintUniqPostalAddCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public PrintUniqPostalAddCommand(ServerCommandProcessor serverCommandProcessor) {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public Response execute(String argument, Object object) {
        try {
            return new Response(ResponseCode.OK, "Command has been successfully executed", serverCommandProcessor.printUniquePostalAddress());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }

}
