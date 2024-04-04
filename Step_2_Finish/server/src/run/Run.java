package run;

import server.Server;

public class Run {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            Server server1 = new Server("5000","D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\test.csv");
            server1.run();
        });
        Thread thread2 = new Thread(() -> {
            Server server2 = new Server("5001","D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\test.csv");
            server2.run();
        });
        thread1.start();
        thread2.start();

    }
}
