package run;

import server.Communicator;
import utility.csv.CSVProcess;


public class Server {
    public static void main(String[] args) {
//        if (args.length == 0 || args[0].trim().length() == 0 || args.length == 1) {
//            ConsolePrinter.printError("You must have entered the port and file from which to load the collection.\nTry again!");
//            System.exit(0);
//        }
        String fileName = "D:\\Admin\\IdeaProjects\\Step_2\\output\\test.csv";
//        String fileName = args[1];
        CSVProcess.loadCollection(fileName);
        Runtime.getRuntime().addShutdownHook(new Thread(CSVProcess::writeCollection));
        Communicator communicator = new Communicator();
        communicator.run("2223");
//        communicator.run(args[0]);
    }
}