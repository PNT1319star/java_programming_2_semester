package processing.specificcommands;

import processing.ServerCommandProcessor;
import java.io.IOException;

public class AddIfMaxCommand extends AbstractCommand{

    public AddIfMaxCommand() {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor serverCommandProcessor = new ServerCommandProcessor();
            return serverCommandProcessor.addIfMax(object);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
