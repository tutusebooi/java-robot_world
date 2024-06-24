package za.co.wethinkcodetest.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.MoveRightCommand;
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

public class MoveRightCommandTest {

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
    public void testMoveRightCommandSuccessfulTurn() {
        GsonRequest request = new GsonRequest(robot, "moveRight", new ArrayList<>());
        MoveRightCommand moveRightCommand = new MoveRightCommand(request);

        GsonResponse response = moveRightCommand.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("Turned right.", response.getData().get("Message"));
        assertEquals(Direction.EAST.toString(), response.getState().get("CURRENT DIRECTION"));
        assertEquals("[1, 1]", response.getState().get("POSITION"));
    }

    @Test
    public void testMoveRightCommandUnlaunchedRobot() {
        GsonRequest request = new GsonRequest(null, "moveRight", new ArrayList<>());
        MoveRightCommand moveRightCommand = new MoveRightCommand(request);

        GsonResponse response = moveRightCommand.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("You first need to launch a robot!!", response.getData().get("Message"));
    }
}

