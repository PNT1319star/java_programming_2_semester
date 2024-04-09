package processing.specificcommands;

import exceptions.WrongAmountOfElementsException;
import processing.ClientCommandProcessor;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class ExecuteScriptCommand extends AbstractCommand implements Serializable {
    final transient private ClientCommandProcessor commandProcessor;
    @Serial
    private static final long serialVersionUID = 32L;
    private static String path;

    public ExecuteScriptCommand(ClientCommandProcessor processor) {
        super("execute_script file_name", "read and execute the script from the specified file.");
        this.commandProcessor = processor;
    }

    @Override
    public void execute(String[] arg) throws StackOverflowError, WrongAmountOfElementsException {
        try {
            if (arg.length != 2) throw new WrongAmountOfElementsException();
            ExecuteScriptCommand.path = arg[1];
            commandProcessor.executeScript(path);
        } catch (StackOverflowError | IOException error) {
            ConsolePrinter.printError("Stack overflow occurred");
        }
    }

    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
