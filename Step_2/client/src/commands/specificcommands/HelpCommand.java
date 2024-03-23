package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.Serializable;

/**
 * The HelpCommand class represents a command that provides help information.
 * This command is typically used to display information about how to use the application
 * or to provide details about other available commands.
 */
public class HelpCommand extends AbstractCommand implements Serializable {

    transient private Receiver receiver;
    private static final long serialVersionUID = 32L;

    /**
     * Constructs a HelpCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public HelpCommand(Receiver receiver) {
        super("help", "display help on available commands");
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] arg) {
        receiver.help();
    }

    /**
     * Retrieves information about the help command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
