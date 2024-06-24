package za.co.wethinkcodetest.gson;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GsonRequestTest {

    @Test
    public void testGetGsonArgument() {
        ArrayList<String> argument = new ArrayList<>();
        argument.add("arg1");
        argument.add("arg2");
        AbstractRobot robot = new MyRobot("Kyle "); // Create your own implementation of AbstractRobot
        GsonRequest request = new GsonRequest(robot, "command", argument);

        assertEquals(argument, request.getGsonArgument());
    }

    @Test
    public void testGetGSonRobot() {
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        ArrayList<String> argument = new ArrayList<>();
        GsonRequest request = new GsonRequest(robot, "command", argument);

        assertEquals(robot, request.getGSonRobot());
    }

    @Test
    public void testGetGsonCommand() {
        AbstractRobot robot = new MyRobot("Kyle"); // Create your own implementation of AbstractRobot
        ArrayList<String> argument = new ArrayList<>();
        GsonRequest request = new GsonRequest(robot, "command", argument);

        assertEquals("command", request.getGsonCommand());
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
