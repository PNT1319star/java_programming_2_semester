package run;

import file.CSVProcess;
import server.Server;
import utilities.CollectionManager;

public class Run {
    public static void main(String[] args) {
//        String port = args[0];
//        String pathToFile = args[1];
        String pathToFile = "D:\\Admin\\IdeaProjects\\Step_2\\test\\test.csv";
        String port = "2222";
        CSVProcess.setPathToFile(pathToFile);
        CollectionManager.getCollectionFromFile(pathToFile);
        Runtime.getRuntime().addShutdownHook(new Thread(CSVProcess::writeCollection));
        Server server = new Server(port);
        server.run();
    }
}