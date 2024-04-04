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
            command.execute(argument);
        }
        if (receivedObject instanceof SerializedSimpleCommand) {
            SerializedSimpleCommand simpleCommand = (SerializedSimpleCommand) receivedObject;
            AbstractCommand command = simpleCommand.getCommand();
            String argument = "";
            command.execute(argument);
        }
        if (receivedObject instanceof SerializedCommandWithArgument) {
            SerializedCommandWithArgument commandWithArgument = (SerializedCommandWithArgument) receivedObject;
            AbstractCommand command = commandWithArgument.getCommand();
            String argument = commandWithArgument.getArgument();
            command.execute(argument);
        }
        if (receivedObject instanceof SerializedCommandWithObjectAndArgument) {
            SerializedCommandWithObjectAndArgument commandWithObjectAndArgument = (SerializedCommandWithObjectAndArgument) receivedObject;
            AbstractCommand command = commandWithObjectAndArgument.getCommand();
            command.execute(command);
        }
    }


}
