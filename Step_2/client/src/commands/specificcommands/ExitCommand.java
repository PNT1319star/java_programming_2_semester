package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.Serial;
import java.io.Serializable;

/**
 * The ExitCommand class represents a command to end the program without saving to a file.
 * It extends the AbstractCommand class.
 */
public class ExitCommand extends AbstractCommand implements Serializable {
    final transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    /**
     * Constructs an ExitCommand object with the specified receiver.
     *
     * @param receiver the receiver to end the program
     */
    public ExitCommand(Receiver receiver) {
        super("exit", "end the program (without saving to a file).");
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] arg) {
        receiver.exit();
    }

    /**
     * Retrieves information about the exit command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
