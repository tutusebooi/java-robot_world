package za.co.wethinkcodetest.gson;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GsonResponseTest {

    @Test
    public void testGetGsonResultData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", data, new HashMap<>(), true, robot);

        assertEquals(data, response.getGsonResultData());
    }

    @Test
    public void testGetState() {
        HashMap<String, String> state = new HashMap<>();
        state.put("stateKey1", "stateValue1");
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", new HashMap<>(), state, true, robot);

        assertEquals(state, response.getState());
    }

    @Test
    public void testGetData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        AbstractRobot robot = new MyRobot("kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", data, new HashMap<>(), true, robot);

        assertEquals(data, response.getData());
    }

    @Test
    public void testIsStillInPlay() {
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", new HashMap<>(), new HashMap<>(), true, robot);

        assertEquals(true, response.isStillInPlay());
    }

    @Test
    public void testGetResult() {
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", new HashMap<>(), new HashMap<>(), true, robot);

        assertEquals("result", response.getResult());
    }

    @Test
    public void testGetGsonResultState() {
        HashMap<String, String> state = new HashMap<>();
        state.put("stateKey1", "stateValue1");
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", new HashMap<>(), state, true, robot);

        assertEquals(state, response.getGsonResultState());
    }

    @Test
    public void testGetRobot() {
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        GsonResponse response = new GsonResponse("result", new HashMap<>(), new HashMap<>(), true, robot);

        assertEquals(robot, response.getRobot());
    }

    // Create your own implementation of AbstractRobot for testing purposes
    private static class MyRobot extends AbstractRobot {
        protected MyRobot(String name) {
            super(name);
        }

        @Override
        public String theTypeOfRobotSet() {
            return null;
        }

        @Override
        public int theDefaultNumberOfAmmo() {
            return 0;
        }

        @Override
        public int theDefaultDistance() {
            return 0;
        }

        @Override
        public boolean executeShootCommand(World world) {
            return false;
        }
    }
}
