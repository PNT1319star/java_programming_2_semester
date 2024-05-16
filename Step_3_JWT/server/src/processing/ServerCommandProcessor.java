package processing;

import authentication.JWTEncoder;
import data.Organization;
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

    public ServerCommandProcessor(DatabaseUserManager databaseUserManager) {
        this.databaseUserManager = databaseUserManager;
    }

    public String help() throws IOException {
        StringBuilder sb = new StringBuilder();
        Invoker.getCommandsList().forEach((name, command) -> sb.append(command.getCommandInformation()).append("\n"));
        return sb.toString();
    }

    public String add(Object object) throws IOException {
        return CollectionManager.add((OrganizationRaw) object);
    }

    public String addIfMax(Object object) throws IOException {
        return CollectionManager.addIfMax((OrganizationRaw) object);
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
        return CollectionManager.removeLower((OrganizationRaw) object);
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

    public String changeRole(String username,String role) {
        try {
            if (databaseUserManager.updateUserRole(username, role)) return "Role of " + username + " has been changed!";
            else return "Nothing has been changed!";
        } catch (HandlingDatabaseException exception) {
            return "An error occurred while accessing to the database!";
        }
    }
}
