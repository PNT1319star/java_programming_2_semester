package processing;

import database.DatabaseUserManager;
import org.csjchoisoojong.data.Organization;
import org.csjchoisoojong.exceptions.HandlingDatabaseException;
import org.csjchoisoojong.exceptions.NotUpdateException;
import org.csjchoisoojong.interaction.OrganizationRaw;
import org.csjchoisoojong.interaction.User;
import utilities.CollectionManager;

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

    public String removeById(String sID) throws IOException {
        int Id = Integer.parseInt(sID);
        if (!CollectionManager.idExistence(Id)) return "OrganizationNotExistException";
        return CollectionManager.removeByID(sID);
    }

    public String removeLower(Object object) throws IOException {
        return CollectionManager.removeLower((OrganizationRaw) object);
    }

    public String update(String sID, Object object) throws IOException {
        int ID = Integer.parseInt(sID);
        if (CollectionManager.idExistence(ID)) {
            if (CollectionManager.updateElement((OrganizationRaw) object, ID))
                return "UpdateSuccess";
            else return "UpdateFailure";
        } else {
            return "OrganizationNotExistException";
        }
    }

    public String login(Object object) throws IOException {
        try {
            User user = (User) object;
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) {
                int user_id = databaseUserManager.selectUserIdByUsername(user.getUsername());
                return databaseUserManager.getSessionIdByUserId(user_id);
            } else throw new IOException();
        } catch (HandlingDatabaseException exception) {
            return "An error occurred while accessing to the database!";
        }
    }

    public String register(Object object) throws IOException{
        try {
            User user = (User) object;
            int user_id = databaseUserManager.insertUserIntoUserTable(user);
            return databaseUserManager.insertUserIdIntoSessionsTable(user_id);
        } catch (HandlingDatabaseException | NotUpdateException exception) {
            throw new IOException();
        }
    }
    public ArrayDeque<Organization> getCollection() {
        return CollectionManager.getCollection();
    }
}
