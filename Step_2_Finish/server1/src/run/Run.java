package run;

import file.CSVProcess;
import file.ServerInfoWriter;
import server.Server;
import utilities.CollectionManager;

public class Run {
    public static void main(String[] args) {
//        String pathToFile = args[1];
        String pathToFile = "D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\test.csv";
        String port = "5000";
        ServerInfoWriter.writeServerInfo(port, pathToFile);
        CSVProcess.setPathToFile(pathToFile);
        CollectionManager.getCollectionFromFile(pathToFile);
        Runtime.getRuntime().addShutdownHook(new Thread(CSVProcess::writeCollection));
        Server server1 = new Server(port);
        server1.run();


    }
}