package commands;

import exceptions.WrongAmountOfElementsException;

import java.util.Scanner;

public class RollBackCommand extends AbstractCommand {
    private final Receiver receiver;
    public RollBackCommand (Receiver receiver) {
        super("roll_back", "undo the change mades.");
        this.receiver = receiver;
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        receiver.rollBack(arg[1]);
    }

    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
