package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveFunctionCommand extends AbstractCommand{
    private final ServerCommandProcessor serverCommandProcessor;

    public RemoveFunctionCommand(ServerCommandProcessor serverCommandProcessor) {
        super("remove_function {role} {function}", " remove a function for role (only for admin)");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.removeFunction(argument, (String)object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
