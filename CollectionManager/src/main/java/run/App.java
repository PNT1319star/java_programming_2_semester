package run;


import utility.csv.CSVProcess;
import utility.mode.UserInputMode;

public class App {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].trim().length() == 0) {
            System.out.println("You have not entered the name of the file from which you want to load the collection!");
            System.exit(0);
        }
        System.out.println("Welcome to our application!");
        System.out.println("You need help ? Use command 'help' to get the available command list!");
//        CSVProcess.setPathToFile("D:\\Admin\\IdeaProjects\\CollectionManager\\output\\data\\test2.csv");
        CSVProcess.setPathToFile(args[0]);
        UserInputMode.interactiveMode();
    }
}
