package processing.specificcommands;

import database.DatabaseUserManager;

public class RegisterCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public RegisterCommand(DatabaseUserManager databaseUserManager) {
        super("register", "inner command");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public String execute(String string, Object object) {
        return null;
    }

    @Override
    public String getCommandInformation() {
        return null;
    }
}
