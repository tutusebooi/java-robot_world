package za.co.wethinkcodetest.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.StateCommand;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StateCommandTest {

    private World world;
    private AbstractRobot robot;

    @BeforeEach
    public void setup() {
        world = new World();
        robot = new Robot("TestRobot");
        robot.setPosition(new Position(1, 1));
        robot.setCurrentDirection(Direction.NORTH);
        robot.getRobotBullet().setBulletShootCapacity(10);
        robot.getRobotShield().setNrOfShield(3);
        world.addRobotToWorld(robot);
    }

    @Test
    public void testStateCommand() {
        GsonRequest request = new GsonRequest(robot, "state", new ArrayList<>());
        StateCommand stateCommand = new StateCommand(request);

        GsonResponse response = stateCommand.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("Successful", response.getData().get("Message"));

        assertTrue(response.getState().containsKey("NAME"));
        assertEquals("TestRobot", response.getState().get("NAME"));

        assertTrue(response.getState().containsKey("POSITION"));
        assertEquals("[1, 1]", response.getState().get("POSITION"));

        assertTrue(response.getState().containsKey("CURRENT DIRECTION"));
        assertEquals("NORTH", response.getState().get("CURRENT DIRECTION"));

        assertTrue(response.getState().containsKey("BULLET COUNT"));
        assertEquals("10", response.getState().get("BULLET COUNT"));

        assertTrue(response.getState().containsKey("SHIELD COUNT"));
        assertEquals("3", response.getState().get("SHIELD COUNT"));
    }
}

