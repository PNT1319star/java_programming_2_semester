package processing;

import data.Organization;
import loadbalancerconnector.Sender;
import utilities.CollectionManager;

import java.io.IOException;
import java.text.CollationElementIterator;

public class ServerCommandProcessor {
    public void add(Object object) throws IOException {
        String answer = CollectionManager.add((Organization) object);
        Sender.send(answer.getBytes());
    }

    public void addIfMax(Object object) throws IOException {
        String answer = CollectionManager.addIfMax((Organization) object);
        Sender.send(answer.getBytes());
    }

    public void info() throws IOException {
        String answer = CollectionManager.info();
        Sender.send(answer.getBytes());
    }

    public void clear() throws IOException {
        String answer = CollectionManager.clear();
        Sender.send(answer.getBytes());
    }

    public void filterStartsWithFullName(String fullName) throws IOException {
        String answer = CollectionManager.filterStartsWithFullName(fullName);
        Sender.send(answer.getBytes());
    }

    public void head() throws IOException {
        String answer = CollectionManager.head();
        Sender.send(answer.getBytes());
    }

    public void minByCreationDate() throws IOException {
        String answer = CollectionManager.minByCreationDate();
        Sender.send(answer.getBytes());
    }

    public void show() throws IOException {
        String answer = CollectionManager.show();
        Sender.send(answer.getBytes());
    }

    public void printUniquePostalAddress() throws IOException {
        String answer = CollectionManager.printUniquePostalAddress();
        Sender.send(answer.getBytes());
    }

    public void removeById(String sID) throws IOException {
        String answer = CollectionManager.removeByID(sID);
        Sender.send(answer.getBytes());
    }

    public void removeLower(Object object) throws IOException {
        String answer = CollectionManager.removeLower((Organization) object);
        Sender.send(answer.getBytes());
    }
    public void update(String sID, Object object) throws IOException {
        int ID = Integer.parseInt(sID);
        String answer;
        if (CollectionManager.idExistence(ID)) {
            CollectionManager.updateElement((Organization) object, ID);
            answer = "The organization has been updated";
        } else {
            answer = "This ID does not exist in this collection!";
        }
        Sender.send(answer.getBytes());
    }
}
