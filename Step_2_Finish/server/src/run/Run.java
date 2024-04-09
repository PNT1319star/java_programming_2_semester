package run;

import file.CSVProcess;
import server.Server;
import utilities.CollectionManager;
import utility.ConsolePrinter;

public class Run {
    public static void main(String[] args) {
//        String pathToFile = args[1];
        String pathToFile = "D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\test.csv";
        CSVProcess.setPathToFile(pathToFile);
        CollectionManager.getCollectionFromFile(pathToFile);
        Runtime.getRuntime().addShutdownHook(new Thread(CSVProcess::writeCollection));
        Server server1 = new Server("5000");
        server1.run();


    }
}
