package run;


import utility.csv.CSVProcess;
import utility.mode.UserInputMode;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to our application!");
        System.out.println("You need help ? Use command 'help' to get the available command list!");
        CSVProcess.setPathToFile("D:\\Admin\\IdeaProjects\\CollectionManager\\output\\data\\test.csv");
        UserInputMode.interactiveMode();
    }
}
