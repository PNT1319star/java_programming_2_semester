package run;

import server.Server;

public class Run {
    public static void main(String[] args) {
        Server server1 = new Server("5000", "D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\test.csv");
        server1.run();

    }
}
