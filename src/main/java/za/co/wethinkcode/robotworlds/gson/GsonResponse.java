package za.co.wethinkcode.robotworlds.gson;

import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;

import java.util.HashMap;

public class GsonResponse {
    private String result;
    private HashMap data;
    private HashMap state;
    private boolean isStillInPlay;
    private AbstractRobot robot;

    public GsonResponse(String result, HashMap data, HashMap state, boolean isStillInPlay, AbstractRobot robotUser) {
        this.result = result;
        this.data = data;
        this.state = state;
        this.isStillInPlay = isStillInPlay;
        this.robot = robotUser;
    }

    public HashMap getGsonResultData() {
        return data;
    }

    public HashMap getState() {
        return state;
    }

    public HashMap getData() {
        return data;
    }

    public boolean isStillInPlay() {
        return isStillInPlay;
    }

    public String getResult() {
        return result;
    }

    public HashMap getGsonResultState() {
        return state;
    }

    public AbstractRobot getRobot() {
        return robot;
    }
}
