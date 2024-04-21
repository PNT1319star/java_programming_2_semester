package processing.specificcommands;

public class LoginCommand extends AbstractCommand {
    public LoginCommand() {
        super("login", "inner command");
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
