package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class HeadCommand extends AbstractCommand {
    public HeadCommand() {
        super("head", "print the first element of the collection.");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.head();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
