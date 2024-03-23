package server;

public class Communicator {
    public void run(String sPort) {
        CommandHandler commandHandler = new CommandHandler();
        CommandListener commandListener = new CommandListener(commandHandler);
        SaveCommandListener saveCommandListener = new SaveCommandListener();
        int port = Integer.parseInt(sPort);
        commandListener.startListening(port);
        saveCommandListener.startListening();
    }
}
