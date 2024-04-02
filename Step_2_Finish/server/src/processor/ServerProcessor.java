package processor;

import loadbalancerconnector.Receiver;
import loadbalancerconnector.Sender;
import processor.serializedcommands.SerializedCommandWithObject;
import processor.specificcommands.ServerAbstractCommand;
import utility.ConsolePrinter;

import java.io.IOException;

public class ServerProcessor {
    private Receiver receiver;
    private Sender sender;
    public ServerProcessor(Receiver receiver, Sender sender) {
        this.receiver = receiver;
        this.sender = sender;
    }
    public void decodeAndProcessCommand() throws IOException {
        Object receivedObject = receiver.receive();
        if (receivedObject instanceof SerializedCommandWithObject) {
            SerializedCommandWithObject commandWithObject = (SerializedCommandWithObject) receivedObject;
            ServerAbstractCommand command = commandWithObject.getCommand();
            Object argument = commandWithObject.getObject();
            byte[] answer = command.execute(argument);
            sender.send(answer);
        }
    }


}
