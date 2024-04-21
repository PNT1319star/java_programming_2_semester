package processing;

import data.Organization;
import utilities.CollectionManager;
import utilities.Invoker;

import java.io.IOException;

public class ServerCommandProcessor {
    public String help() throws IOException {
        StringBuilder sb = new StringBuilder();
        Invoker.getCommands().forEach((name, command) -> sb.append(command.getCommandInformation()).append("\n"));
        return sb.toString();
    }
    public String add(Object object) throws IOException {
        return CollectionManager.add((Organization) object);
    }

    public String addIfMax(Object object) throws IOException {
        return CollectionManager.addIfMax((Organization) object);
    }

    public String info() throws IOException {
        return CollectionManager.info();
    }

    public String clear() throws IOException {
        return CollectionManager.clear();
    }

    public String filterStartsWithFullName(String fullName) throws IOException {
        return CollectionManager.filterStartsWithFullName(fullName);
    }

    public String head() throws IOException {
        return CollectionManager.head();
    }

    public String minByCreationDate() throws IOException {
        return CollectionManager.minByCreationDate();
    }

    public String show() throws IOException {
        return CollectionManager.show();
    }

    public String printUniquePostalAddress() throws IOException {
        return CollectionManager.printUniquePostalAddress();
    }

    public String removeById(String sID) throws IOException {
        return CollectionManager.removeByID(sID);
    }

    public String removeLower(Object object) throws IOException {
        return CollectionManager.removeLower((Organization) object);
    }
    public String update(String sID, Object object) throws IOException {
        int ID = Integer.parseInt(sID);
        if (CollectionManager.idExistence(ID)) {
            CollectionManager.updateElement((Organization) object, ID);
            return "The organization has been updated";
        } else {
            return "This ID does not exist in this collection!";
        }
    }
}
