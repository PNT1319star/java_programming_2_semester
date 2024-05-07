package run;

import server.Server;

public class Run {
    public static void main(String[] args) {
//        String port = args[0];
        String databaseHost = "s374807";
        String databasePassword = "9cs4jxVhIg4Vqi8D";
        String databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/studs";
        String port = "2222";
        Server server = new Server(port, databaseAddress, databaseHost, databasePassword);
        server.run();
    }
}