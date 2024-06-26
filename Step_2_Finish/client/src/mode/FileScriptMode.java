package mode;

import processing.ClientCommandProcessor;
import processing.CommandManager;
import utilities.ConsoleManager;
import utility.ConsolePrinter;
import utility.OrganizationBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileScriptMode implements IMode {
    private final String argument;
    private boolean flag = true;
    private int count = 0;
    private int number = 0;
    private int commandStatus = 0;
    private final List<String> scriptStack = new ArrayList<>();
    private final ClientCommandProcessor processor = ConsoleManager.getProcessor();
    private Scanner userScanner;

    /**
     * Constructs a FileScriptMode object with the specified script file argument.
     *
     * @param argument The path to the script file.
     */
    public FileScriptMode(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the commands from the specified script file.
     */
    @Override
    public void executeMode() {
        OrganizationBuilder organizationBuilder = new OrganizationBuilder(new Scanner(System.in));
        Scanner tmpScanner = organizationBuilder.getUserScanner();
        CommandManager.startCommand(processor);
        try {
            initializeScript(this.argument);
            organizationBuilder.setUserScanner(userScanner);
            organizationBuilder.setFileMode();
            do {
                String userCommand = getNextUserCommand();
                handleUserCommand(userCommand);
            } while (commandStatus == 1 && userScanner.hasNextLine());

        } catch (FileNotFoundException exception) {
            ConsolePrinter.printError("Find not found!");
        } catch (NoSuchElementException exception) {
            ConsolePrinter.printError("The script file is empty!");
        } catch (IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected error!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        organizationBuilder.setUserScanner(tmpScanner);
        organizationBuilder.setUserMode();
    }

    /**
     * Initializes the script by creating a scanner for reading input from the script file.
     *
     * @param argument The path to the script file.
     * @throws FileNotFoundException  if the script file is not found.
     * @throws NoSuchElementException if the script file is empty.
     */
    public void initializeScript(String argument) throws FileNotFoundException, NoSuchElementException {
        this.userScanner = new Scanner(new File(argument));
        if (!userScanner.hasNext()) throw new NoSuchElementException();
        scriptStack.add(argument);
    }

    /**
     * Retrieves the next user command from the script file.
     *
     * @return The next user command.
     */
    public String getNextUserCommand() {
        String userCommand = this.userScanner.nextLine().trim();
        ConsolePrinter.printInformation(">" + userCommand);
        return userCommand;
    }

    /**
     * Handles the execution of a user command.
     *
     * @param userCommand The user command to be executed.
     */
    public void handleUserCommand(String userCommand) throws IOException {
        String[] commandPart = userCommand.split(" ", 2);
        String commandName = commandPart[0].trim();
        String commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
        while (this.userScanner.hasNextLine() && commandName.isEmpty()) {
            commandPart = userCommand.split(" ", 2);
            commandName = commandPart[0].trim();
            commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
        }
        switch (commandName) {
            case "add":
                processor.add(this.userScanner);
                commandStatus = 1;
                break;
            case "update":
                processor.update(commandArgument, userScanner);
                commandStatus = 1;
                break;
            case "remove_lower":
                processor.removeLower(userScanner);
                commandStatus = 1;
                break;
            case "add_if_max":
                processor.addIfMax(userScanner);
                commandStatus = 1;
                break;
            case "execute_script":
                handleExecuteScriptCommand(commandArgument);
                commandStatus = 1;
                break;
            default:
                commandStatus = processor.getInvoker().executeCommand(commandPart);
        }
    }

    /**
     * Handles the execution of an execute_script command.
     *
     * @param scriptFile The path to the script file to be executed.
     */
    public void handleExecuteScriptCommand(String scriptFile) {
        for (String script : scriptStack) {
            if (scriptFile.toLowerCase().equals(script)) {
                if (this.flag) {
                    askAction();
                }
                count += 1;
                this.flag = false;
                while (count <= number) {
                    ConsolePrinter.printResult("The execute_script command has been execute " + count + " - time!");
                    scriptStack.clear();
                    this.executeMode();
                    System.out.println(scriptStack);
                }
                break;
            } else {
                FileScriptMode newFileScriptMode = new FileScriptMode(scriptFile);
                newFileScriptMode.executeMode();
                break;
            }
        }
    }

    /**
     * Asks the user for the number of times to execute the execute_script command.
     */
    public void askTimes() {
        while (true) {
            ConsolePrinter.printResult("How many times do you want to execute this command?");
            Scanner scanner2 = new Scanner(System.in);
            String scn2 = scanner2.nextLine().trim();
            try {
                this.number = Integer.parseInt(scn2);
                break;
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("Please enter a valid number!");
            }
        }
    }

    /**
     * Asks the user whether to execute the execute_script command.
     */
    public void askAction() {
        ConsolePrinter.printResult("The execute_script command appears in the script file!");
        ConsolePrinter.printResult("Do you want to execute this command? (yes / no)");
        Scanner scanner1 = new Scanner(System.in);
        String scn1 = scanner1.nextLine().trim().toLowerCase();
        while (true) {
            if (scn1.equals("yes")) {
                this.askTimes();
                break;
            } else if (scn1.equals("no")) {
                break;
            } else {
                ConsolePrinter.printError("Invalid input. Please enter 'yes' or 'no'!");
                break;
            }
        }
    }
}