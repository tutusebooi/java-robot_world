package za.co.wethinkcodetest.commands;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.MoveBackwardCommand;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;
//import za.co.wethinkcode.robotworlds.world.obstacle.Obstacle;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MoveBackCommandTest {
    private World world;
    private AbstractRobot robot;

    @BeforeEach
    public void setUp() {
        world = new World();
        robot = new Robot("TestRobot");
        robot.setPosition(new Position(0, 0));
        world.addRobotToWorld(robot);
    }

    @Test
    public void testMoveBackwardCommandRobotNotLaunched() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("3"); // Number of steps

        GsonRequest request = new GsonRequest(null, "moveBackward", arguments);
        MoveBackwardCommand moveBackwardCommand = new MoveBackwardCommand(request);

        world.removeRobotFromWorld(robot);

        GsonResponse response = moveBackwardCommand.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("You first need to launch a robot!!", response.getData().get("Message"));
    }

    @Test
    public void testMoveBackwardCommandSuccessfulMovement() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("3"); // Number of steps to move backward

        // position within world boundaries
        robot.setPosition(new Position(0, 0));
        world.addRobotToWorld(robot);

        GsonRequest request = new GsonRequest(robot, "moveBackward", arguments);
        MoveBackwardCommand moveBackwardCommand = new MoveBackwardCommand(request);

        GsonResponse response = moveBackwardCommand.executeCommand(world);

        System.out.println("Test Response: " + response.getResult()); // Debugging
        System.out.println("Test Data: " + response.getData()); // Debugging
        System.out.println("Test State: " + response.getState()); // Debugging

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("Moved backward by 3 steps.", response.getData().get("Message"));
        assertEquals(new Position(199, 196).toString(), response.getState().get("POSITION"));
    }

}
