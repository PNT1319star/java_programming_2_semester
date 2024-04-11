package run;

import utilities.LoadBalancerManager;

import java.io.ObjectOutputStream;

public class LoadBalancer {
    public static void main(String[] args) {
        LoadBalancerManager.start("D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\loadbalancer.yaml");
    }
}