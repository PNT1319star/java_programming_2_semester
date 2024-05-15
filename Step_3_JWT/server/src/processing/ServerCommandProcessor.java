package processing;

import authentication.JWTEncoder;
import data.Organization;
import database.DatabaseCollectionManager;
import database.DatabaseUserManager;
import exceptions.HandlingDatabaseException;
import interaction.OrganizationRaw;
import interaction.User;
import utilities.CollectionManager;
import utilities.Invoker;
import utilities.Roles;

import java.io.IOException;
import java.util.List;

public class ServerCommandProcessor {
    private final DatabaseUserManager databaseUserManager;
    private final CollectionManager collectionManager;

    public ServerCommandProcessor(DatabaseUserManager databaseUserManager, DatabaseCollectionManager databaseCollectionManager) {
        this.databaseUserManager = databaseUserManager;
        this.collectionManager = new CollectionManager(databaseCollectionManager);
    }

    public String help() throws IOException {
        StringBuilder sb = new StringBuilder();
        Invoker.getCommands().forEach((name, command) -> sb.append(command.getCommandInformation()).append("\n"));
        return sb.toString();
    }

    public String add(Object object) throws IOException {
        return collectionManager.add((OrganizationRaw) object);
    }

    public String addIfMax(Object object, String userName) throws IOException {
        return collectionManager.addIfMax((OrganizationRaw) object, userName);
    }

    public String info() throws IOException {
        return collectionManager.info();
    }

    public String clear(String userName) throws IOException {
        return collectionManager.clear(userName);
    }

    public String filterStartsWithFullName(String fullName) throws IOException {
        return collectionManager.filterStartsWithFullName(fullName);
    }

    public String head() throws IOException {
        return collectionManager.head();
    }

    public String minByCreationDate() throws IOException {
        return collectionManager.minByCreationDate();
    }

    public String show() throws IOException {
        return collectionManager.show();
    }

    public String printUniquePostalAddress() throws IOException {
        return collectionManager.printUniquePostalAddress();
    }

    public String removeById(String sID, String userName) throws IOException {
        return collectionManager.removeByID(sID, userName);
    }

    public String removeLower(Object object, String userName) throws IOException {
        return collectionManager.removeLower((OrganizationRaw) object, userName);
    }

    public String update(String sID, Object object) throws IOException {
        int ID = Integer.parseInt(sID);
        if (collectionManager.idExistence(ID)) {
            collectionManager.updateElement((Organization) object, ID);
            return "The organization has been updated";
        } else {
            return "This ID does not exist in this collection!";
        }
    }

    public String login(Object object) {
        try {
            User user = (User) object;
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                Roles role = databaseUserManager.getUserRole(user);
                List<String> functionList =databaseUserManager.getFunctionList(role);
                return JWTEncoder.encodeJWT(user.getUsername(), role, functionList);
            } else return "Incorrect username or password!";
        } catch (HandlingDatabaseException exception) {
            return "An error occurred while accessing to the database!";
        }
    }

    public String register(Object object) {
        try {
            User user = (User) object;
            if (databaseUserManager.insertUser(user)) return "User " + user.getUsername() + " registered!";
            else return "User " + user.getUsername() + " has been existed!";
        } catch (HandlingDatabaseException exception) {
            return "An error occurred while accessing to the database!";
        }
    }

    public String changeRole(String role) {
        try {

        }
    }
}
