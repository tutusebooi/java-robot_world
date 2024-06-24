package za.co.wethinkcodetest.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.QuitCommand;
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

public class QuitCommandTest {

    private World world;
    private AbstractRobot robot;

    @BeforeEach
    public void setup() {
        world = new World();
        robot = new Robot("TestRobot");
        robot.setPosition(new Position(1, 1));
        robot.setCurrentDirection(Direction.NORTH);
        world.addRobotToWorld(robot);
    }

    @Test
    public void testQuitCommandSuccessful() {
        GsonRequest request = new GsonRequest(robot, "quit", new ArrayList<>());
        QuitCommand quitCommand = new QuitCommand(request);

        GsonResponse response = quitCommand.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("Ending session...", response.getData().get("Message"));
        assertEquals("[1, 1]", response.getState().get("POSITION"));
        assertEquals("NORTH", response.getState().get("CURRENT DIRECTION"));
        assertEquals("TestRobot", response.getState().get("NAME"));
    }

    @Test
    public void testQuitCommandUnlaunchedRobot() {
        GsonRequest request = new GsonRequest(null, "quit", new ArrayList<>());
        QuitCommand quitCommand = new QuitCommand(request);

        GsonResponse response = quitCommand.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("You first need to create a robot!!", response.getData().get("Message"));
    }
}

