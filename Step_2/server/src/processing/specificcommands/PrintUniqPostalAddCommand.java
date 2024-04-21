package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class PrintUniqPostalAddCommand extends AbstractCommand {
    public PrintUniqPostalAddCommand() {
        super("print_unique_postal_address", "print the unique values of the postalAddress field of all elements in the collection.");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.printUniquePostalAddress();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }

}
