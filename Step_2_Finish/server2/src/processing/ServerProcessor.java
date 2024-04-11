package processing;

import loadbalancerconnector.Receiver;
import loadbalancerconnector.Sender;
import processing.serializedcommands.SerializedCommandWithArgument;
import processing.serializedcommands.SerializedCommandWithObject;
import processing.serializedcommands.SerializedCommandWithObjectAndArgument;
import processing.serializedcommands.SerializedSimpleCommand;
import processing.specificcommands.AbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;

public class ServerProcessor {
    private final Receiver receiver;

    public ServerProcessor(Receiver receiver) {
        this.receiver = receiver;
    }

    public void decodeAndProcessCommand() throws IOException {
        Object receivedObject = receiver.receive();
        if (receivedObject instanceof SerializedCommandWithObject) {
            SerializedCommandWithObject commandWithObject = (SerializedCommandWithObject) receivedObject;
            AbstractCommand command = commandWithObject.getCommand();
            Object argument = commandWithObject.getObject();
            ConsolePrinter.printResult(receivedObject);
            command.execute(argument);
        }
        else if (receivedObject instanceof SerializedSimpleCommand) {
            SerializedSimpleCommand simpleCommand = (SerializedSimpleCommand) receivedObject;
            AbstractCommand command = simpleCommand.getCommand();
            String argument = "";
            ConsolePrinter.printResult(receivedObject);
            command.execute(argument);
        }
        else if (receivedObject instanceof SerializedCommandWithArgument) {
            SerializedCommandWithArgument commandWithArgument = (SerializedCommandWithArgument) receivedObject;
            AbstractCommand command = commandWithArgument.getCommand();
            String argument = commandWithArgument.getArgument();
            ConsolePrinter.printResult(receivedObject);
            command.execute(argument);
        }
        else if (receivedObject instanceof SerializedCommandWithObjectAndArgument) {
            SerializedCommandWithObjectAndArgument commandWithObjectAndArgument = (SerializedCommandWithObjectAndArgument) receivedObject;
            AbstractCommand command = commandWithObjectAndArgument.getCommand();
            ConsolePrinter.printResult(receivedObject);
            command.execute(commandWithObjectAndArgument);
        } else {
            String sign = (String) receivedObject;
            if (sign.equals("quit")) {
                System.exit(0);
            }
            if (sign.equals("alive")) {
                Sender.send("alive".getBytes());
            }
        }
    }


}
