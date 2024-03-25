package commands.specificcommands;

import client.Receiver;
import commands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;

/**
 * The UpdateCommand class represents a command to update a collection element by its ID.
 * This command updates the value of a collection element whose ID is equal to a given one.
 */
public class UpdateCommand extends AbstractCommand implements Serializable {
    transient private Receiver receiver;
    @Serial
    private static final long serialVersionUID = 32L;

    public UpdateCommand(Receiver receiver) {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
        this.receiver = receiver;
    }

    public UpdateCommand() {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
    }

    @Override
    public void execute(String[] arg) throws IOException {
        receiver.update(arg[1], new Scanner(System.in));
    }

    /**
     * Displays information about the update command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
