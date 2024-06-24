package za.co.wethinkcode.robotworlds.gson;

import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;

import java.util.ArrayList;

public class GsonRequest {
    private AbstractRobot robot;
    private String command;
    private ArrayList<String> argument;

    public GsonRequest(AbstractRobot robot, String command, ArrayList<String> argument) {
        this.robot = robot;
        this.command = command;
        this.argument = argument;
    }

    public ArrayList<String> getGsonArgument() {
        return argument;
    }

    public AbstractRobot getGSonRobot() {
        return robot;
    }

    public String getGsonCommand() {
        return command;
    }
}
