package run;

import server.Server;

public class Run {
    public static void main(String[] args) {
        Server server1 = new Server("5000");
        server1.run();

    }
}
