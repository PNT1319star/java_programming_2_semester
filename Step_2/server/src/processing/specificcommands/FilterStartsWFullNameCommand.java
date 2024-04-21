package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class FilterStartsWFullNameCommand extends AbstractCommand {
    public FilterStartsWFullNameCommand() {
        super("filter_starts_with_full_name fullName", "display elements whose fullName field value begins with a given substring");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.filterStartsWithFullName(argument);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
