package commands;

import exceptions.WrongAmountOfElementsException;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 * It extends the AbstractCommand class.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final Receiver receiver;
    private static String path;

    /**
     * Constructs an ExecuteScriptCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the script
     */
    public ExecuteScriptCommand(Receiver receiver) {
        super("\u001B[36mexecute_script file_name\u001B[0m", "read and execute the script from the specified file.");
        this.receiver = receiver;
    }

    /**
     * Executes the execute_script command.
     *
     * @param arg the arguments for the command
     * @throws StackOverflowError            if a stack overflow occurs
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws StackOverflowError, WrongAmountOfElementsException {
        try {
            if (arg.length != 2) throw new WrongAmountOfElementsException();
            ExecuteScriptCommand.path = arg[1];
            receiver.executeScript(path);
        } catch (StackOverflowError error) {
            System.out.println("Stack overflow occurred");
        }
    }

    /**
     * Retrieves information about the execute_script command.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
