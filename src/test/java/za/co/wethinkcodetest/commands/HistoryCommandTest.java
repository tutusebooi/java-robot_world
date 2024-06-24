package za.co.wethinkcodetest.commands;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Commands;
import za.co.wethinkcode.robotworlds.commands.MoveBackwardCommand;
import za.co.wethinkcode.robotworlds.commands.MoveForwardCommand;
import za.co.wethinkcode.robotworlds.commands.MoveLeftCommand;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryCommandTest {

    private World world;
    private AbstractRobot robot;

    @BeforeEach
    public void setUp() {
        world = new World();  // Initialize your World object as needed
        robot = new Robot("TestRobot");  // Initialize a robot for testing
        world.addRobotToWorld(robot);  // Add the robot to the world
    }

    @Test
    public void testHistoryCommandExecutionWithHistory() {
        // Create some commands and add them to the commandsArrayList
        Commands.addCommandsArrayList(new MoveForwardCommand(new GsonRequest(robot, "forward", new ArrayList<>())));
        Commands.addCommandsArrayList(new MoveBackwardCommand(new GsonRequest(robot, "back", new ArrayList<>())));
        Commands.addCommandsArrayList(new MoveLeftCommand(new GsonRequest(robot, "left", new ArrayList<>())));

        GsonRequest request = new GsonRequest(robot, "history", new ArrayList<>());
        Commands command = Commands.createCommandGson(request);

        GsonResponse response = command.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("History"));
        assertTrue(response.getData().containsKey("History Count"));
        assertEquals(3, Integer.parseInt((String) response.getData().get("History Count")));
    }

    @Test
    public void testHistoryCommandExecutionWithoutRobot() {
        GsonRequest request = new GsonRequest(null, "history", new ArrayList<>());
        Commands command = Commands.createCommandGson(request);

        GsonResponse response = command.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("You first need to launch a robot!!", response.getData().get("Message"));
    }
}
