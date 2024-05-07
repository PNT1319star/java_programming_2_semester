package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class PrintUniqPostalAddCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public PrintUniqPostalAddCommand(ServerCommandProcessor serverCommandProcessor) {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.printUniquePostalAddress();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }

}
