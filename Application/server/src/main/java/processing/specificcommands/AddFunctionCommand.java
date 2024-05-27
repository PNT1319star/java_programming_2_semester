package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class AddFunctionCommand extends AbstractCommand{
    private final ServerCommandProcessor serverCommandProcessor;

    public AddFunctionCommand(ServerCommandProcessor serverCommandProcessor) {
        super("add_function {role} {function}", " add new function for role (only for admin)");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.addFunction(argument, (String)object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
