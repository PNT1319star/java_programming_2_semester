package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serializable;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 * It extends the AbstractCommand class.
 */
public class ExecuteScriptCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;
    private static String path;

    /**
     * Constructs an ExecuteScriptCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the script
     */
    public ExecuteScriptCommand(Receiver receiver) {
        super("execute_script file_name", "read and execute the script from the specified file.");
        this.receiver = receiver;
    }
    public ExecuteScriptCommand() {
        super("execute_script file_name", "read and execute the script from the specified file.");
    }

    @Override
    public void execute(String[] arg) throws StackOverflowError {
        try {
            ExecuteScriptCommand.path = arg[1];
            receiver.executeScript(path);
        } catch (StackOverflowError | IOException error) {
            ConsolePrinter.printError("Stack overflow occurred");
        }
    }

    /**
     * Retrieves information about the execute_script command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
