package utility.mode;

import commands.Invoker;
import commands.Receiver;
import utility.Console;
import utility.creator.OrganizationBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileScriptMode {
    private static boolean flag = true;
    private static int count = 0;
    private static int number = 0;
    private static List<String> scriptStack = new ArrayList<>();
    private static Receiver receiver = new Receiver(new Invoker());
    private static Console console = new Console(receiver);
    private static Scanner userScanner;
    private static OrganizationBuilder organizationBuilder;
    /**
     * Executes the commands from the specified script file.
     *
     * @param argument The path to the script file.
     */
    public static void scriptMode(String argument) {
        count += 1;
        console.commandStarter();
        String[] userCommand = {"",""};
        int commandStatus = 0;
        scriptStack.add(argument);
        try  {
            userScanner = new Scanner(new File(argument));
            organizationBuilder = new OrganizationBuilder(new Scanner(System.in));
            if (!userScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = organizationBuilder.getUserScanner();
            organizationBuilder.setUserScanner(userScanner);
            organizationBuilder.setFileMode();
            do {
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                System.out.println("> " + String.join(" ", userCommand));
                while (userScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                switch(userCommand[0]) {
                    case "add" :
                        receiver.add(userScanner);
                        commandStatus = 1;
                        break;
                    case "update" :
                        receiver.update(userCommand[1], userScanner);
                        commandStatus = 1;
                        break;
                    case "remove_lower" :
                        receiver.removeLower(userScanner);
                        commandStatus = 1;
                        break;
                    case "add_if_max" :
                        receiver.addIfMax(userScanner);
                        commandStatus = 1;
                        break;
                    case "execute_script" :
                        for (String script : scriptStack) {
                            if (userCommand[1].toLowerCase().equals(script)) {
                                if(FileScriptMode.flag) {
                                    System.out.println("\u001B[33mThe execute_script command appears in the script file!\u001B[0m");
                                    System.out.println(("\u001B[33mDo you want to execute this command?\u001B[0m (\u001B[36myes\u001B[0m / \u001B[36mno\u001B[0m)"));
                                    Scanner scanner1 = new Scanner(System.in);
                                    String scn1 = scanner1.nextLine().trim().toLowerCase();
                                    while (true) {
                                        if (scn1.equals("yes")) {
                                            while (true) {
                                                System.out.println("\u001B[33mHow many times do you want to execute this command?\u001B[0m");
                                                Scanner scanner2 = new Scanner(System.in);
                                                String scn2 = scanner2.nextLine().trim();
                                                try {
                                                    int number = Integer.parseInt(scn2);
                                                    FileScriptMode.number = number;
                                                    break; // Thoát khỏi vòng lặp nếu nhập đúng
                                                } catch (NumberFormatException e) {
                                                    System.out.println("\u001B[31mPlease enter a valid number.\u001B[0m");
                                                }
                                            }
                                        } else if (scn1.equals("no")) {
                                            break; // Thoát khỏi vòng lặp lớn nếu người dùng không muốn tiếp tục
                                        } else {
                                            System.out.println("\u001B[31mInvalid input. Please enter 'yes' or 'no'.\u001B[0m");
                                            break;
                                        }
                                    }
                                }
                                FileScriptMode.flag = false;
                                while(count <= number) {
                                    System.out.printf("\u001B[33mThe execute_script command has been execute %s-time!\u001B[0m",count);
                                    scriptStack.remove(scriptStack.size() - 1);
                                    FileScriptMode.scriptMode(userCommand[1].toLowerCase());
                                }

                                }
                            }
                        commandStatus = 1;
                        break;
                    default:
                        commandStatus = Invoker.executeCommand(userCommand);

                }
            } while (commandStatus == 1 && userScanner.hasNextLine());
            organizationBuilder.setUserScanner(tmpScanner);
            organizationBuilder.setUserMode();
            if (commandStatus == 0 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                System.err.println("Check the script to ensure the entered data is correct!");
            }
        } catch (FileNotFoundException exception) {
            System.err.println("Can not find the file with the script!");
        } catch (NoSuchElementException exception) {
            System.err.println("The script file is empty!");
        } catch (IllegalStateException exception) {
            System.err.println("Unexpected error!");
        }
    }

    /**
     * Retrieves the scanner used for reading input from the script file.
     *
     * @return The scanner used for reading input from the script file.
     */
    public static Scanner getScanner() {
        return userScanner;
    }

}
