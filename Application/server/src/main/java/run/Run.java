package run;

import file.ServerInfoWriter;
import server.Server;

public class Run {
    public static void main(String[] args) {
//        String port = args[0];
//        String databaseHost = args[1];
//        String databasePassword = args[2];
//        String databaseUser = "s374807";
//        String databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/studs";
        String databaseHost = "localhost";
        String databaseUser = "s374807";
        String databasePassword = "9cs4jxVhIg4Vqi8D";
        String databaseAddress = "jdbc:postgresql://" + databaseHost + ":5656/studs";
        String port = "2222";
//        ServerInfoWriter.writeServerInfo(port, databaseUser);
        Server server = new Server(port, databaseAddress, databaseUser, databasePassword);
        server.run();
    }
}