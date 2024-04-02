package processing;

import processing.serializedcommands.SerializedCommandWithObject;
import processing.specificcommands.AddCommand;
import connector.Receiver;
import connector.Sender;
import utilities.Invoker;
import utility.ConsolePrinter;
import utility.OrganizationCreator;

import java.io.IOException;
import java.util.Scanner;

public class ClientCommandProcessor {
    private final Invoker invoker;
    private final Sender sender;
    private final Receiver receiver;
    public ClientCommandProcessor(Invoker invoker, Sender sender, Receiver receiver) {
        this.invoker = invoker;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void add(Scanner scanner) throws IOException, ClassNotFoundException {
        SerializedCommandWithObject serializedAddCommand = new SerializedCommandWithObject(new AddCommand(), OrganizationCreator.organizationCreator(scanner));
        sender.sendObject(serializedAddCommand);
        ConsolePrinter.printResult("sent!");
        byte[] data = receiver.receive();
        ConsolePrinter.printResult("received!");
        String result = new String(data);
        ConsolePrinter.printResult(result);
    }
    public Invoker getInvoker() {
        return invoker;
    }
}
