package za.co.wethinkcodetest.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.MoveForwardCommand;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveForwardCommandTest {

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
    public void testMoveForwardCommandSuccessfulMovement() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("3"); // Number of steps

        GsonRequest request = new GsonRequest(robot, "moveForward", arguments);
        MoveForwardCommand moveForwardCommand = new MoveForwardCommand(request);

        GsonResponse response = moveForwardCommand.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("Moved forward by 3 steps.", response.getData().get("Message"));
        assertEquals(new Position(1, 4).toString(), response.getState().get("POSITION"));
    }

    @Test
    public void testMoveForwardCommandOutOfBounds() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("200");

        GsonRequest request = new GsonRequest(robot, "moveForward", arguments);
        MoveForwardCommand moveForwardCommand = new MoveForwardCommand(request);

        GsonResponse response = moveForwardCommand.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        String message = (String) response.getData().get("Message");
        assertNotNull(message);
        assertTrue(message.contains("outside of the world") || message.contains("boundary"));
    }


    @Test
    public void testMoveForwardCommandRobotNotLaunched() {
        // Creating a new request with a null robot to simulate robot not launched
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("3"); // Number of steps

        GsonRequest request = new GsonRequest(null, "moveForward", arguments);
        MoveForwardCommand moveForwardCommand = new MoveForwardCommand(request);

        GsonResponse response = moveForwardCommand.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("You first need to launch a robot!!", response.getData().get("Message"));
    }
    
}
