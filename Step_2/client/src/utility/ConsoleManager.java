package utility;

import client.Communicator;
import client.Invoker;
import client.Receiver;
import client.Sender;
import commands.specificcommands.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class ConsoleManager {

    public void interactive(String host, String port) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            Communicator communicator = new Communicator(host, Integer.parseInt(port));
            communicator.startCommunication();
            Sender sender = new Sender(communicator);
            Invoker invoker = new Invoker();
            ObjectInputStream inputStream = new ObjectInputStream(communicator.getSocket().getInputStream());
            Receiver receiver = new Receiver(invoker, sender, inputStream);
            invoker.register("help", new HelpCommand(receiver));
            invoker.register("info", new InfoCommand(receiver));
            invoker.register("show", new ShowCommand(receiver));
            invoker.register("add", new AddCommand(receiver));
            invoker.register("update", new UpdateCommand(receiver));
            invoker.register("remove_by_id", new RemoveByIdCommand(receiver));
            invoker.register("clear", new ClearCommand(receiver));
            invoker.register("execute_script", new ExecuteScriptCommand(receiver));
            invoker.register("exit", new ExitCommand(receiver));
            invoker.register("head", new HeadCommand(receiver));
            invoker.register("add_if_max", new AddIfMaxCommand(receiver));
            invoker.register("remove_lower", new RemoveLowerCommand(receiver));
            invoker.register("min_by_creation_date", new MinByCreationDateCommand(receiver));
            invoker.register("filter_starts_with_full_name", new FilterStartsWFullNameCommand(receiver));
            invoker.register("print_unique_postal_address", new PrintUniqPostalAddCommand(receiver));
            while (scanner.hasNextLine()) {
                invoker.executeCommand(scanner.nextLine().trim().split(" "));
            }
            communicator.endCommunication();
        } catch (NumberFormatException exception) {
            ConsolePrinter.printError("Something went wrong with arguments!");
            System.exit(0);
        }
    }
}
