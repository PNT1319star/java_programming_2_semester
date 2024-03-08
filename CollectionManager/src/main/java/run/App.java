package run;


import utility.Console;
import utility.ConsolePrinter;
import utility.csv.CSVProcess;
import utility.mode.UserInputMode;

public class App {
    public static void main(String[] args) {
//        if (args.length == 0 || args[0].trim().length() == 0) {
//            ConsolePrinter.printInformation("You have not entered the name of the file from which you want to load the collection!");
//            System.exit(0);
//        }
        ConsolePrinter.printInformation("Welcome to our application!");
        ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
        Console.starter("D:\\Admin\\IdeaProjects\\CollectionManager\\output\\data\\test.csv");
//        Console.starter(args[0]);
    }
}
