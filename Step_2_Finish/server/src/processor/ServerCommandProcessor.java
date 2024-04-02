package processor;

import data.Organization;
import loadbalancerconnector.Receiver;
import loadbalancerconnector.Sender;
import utilities.CollectionManager;

public class ServerCommandProcessor {
    public byte[] add(Object object) {
        String answer = CollectionManager.add((Organization) object);
        return answer.getBytes();
    }
}
