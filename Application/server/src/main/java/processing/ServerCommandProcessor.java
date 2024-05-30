package processing;

import database.DatabaseUserManager;
import org.csjchoisoojong.data.Organization;
import org.csjchoisoojong.exceptions.HandlingDatabaseException;
import org.csjchoisoojong.exceptions.NotUpdateException;
import org.csjchoisoojong.interaction.OrganizationRaw;
import org.csjchoisoojong.interaction.User;
import utilities.CollectionManager;
import utilities.Invoker;

import java.io.IOException;
import java.util.ArrayDeque;

public class ServerCommandProcessor {
    private final DatabaseUserManager databaseUserManager;

    public ServerCommandProcessor(DatabaseUserManager databaseUserManager) {
        this.databaseUserManager = databaseUserManager;
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

    public ArrayDeque<Organization> filterStartsWithFullName(String fullName) throws IOException {
        return CollectionManager.filterStartsWithFullName(fullName);
    }

    public ArrayDeque<Organization> head() throws IOException {
        return CollectionManager.head();
    }

    public ArrayDeque<Organization> minByCreationDate() throws IOException {
        return CollectionManager.minByCreationDate();
    }


    public ArrayDeque<Organization> printUniquePostalAddress() throws IOException {
        return CollectionManager.printUniquePostalAddress();
    }

    public String removeById(String sID) throws IOException {
        int Id = Integer.parseInt(sID);
        if (!CollectionManager.idExistence(Id)) return "The organization with this id has not existed!";
        return CollectionManager.removeByID(sID);
    }

    public String removeLower(Object object) throws IOException {
        return CollectionManager.removeLower((OrganizationRaw) object);
    }

    public String update(String sID, Object object) throws IOException {
        int ID = Integer.parseInt(sID);
        if (CollectionManager.idExistence(ID)) {
            if (CollectionManager.updateElement((OrganizationRaw) object, ID))
                return "The organization has been updated";
            else return "You can not update this organization";
        } else {
            return "This ID does not exist in this collection!";
        }
    }

    public String login(Object object) {
        try {
            User user = (User) object;
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                int user_id = databaseUserManager.selectUserIdByUsername(user.getUsername());
                return databaseUserManager.getSessionIdByUserId(user_id);
            } else return "Incorrect username or password!";
        } catch (HandlingDatabaseException exception) {
            return "An error occurred while accessing to the database!";
        }
    }

    public String register(Object object) {
        try {
            User user = (User) object;
            int user_id = databaseUserManager.insertUserIntoUserTable(user);
            return databaseUserManager.insertUserIdIntoSessionsTable(user_id);
        } catch (HandlingDatabaseException | NotUpdateException exception) {
            return "User has been existed!";
        }
    }
    public ArrayDeque<Organization> getCollection() {
        return CollectionManager.getCollection();
    }
}
