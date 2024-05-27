package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class FilterStartsWFullNameCommand extends AbstractCommand {
    private final ServerCommandProcessor serverCommandProcessor;

    public FilterStartsWFullNameCommand(ServerCommandProcessor serverCommandProcessor) {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
        this.serverCommandProcessor = serverCommandProcessor;
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            return serverCommandProcessor.filterStartsWithFullName(argument);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
