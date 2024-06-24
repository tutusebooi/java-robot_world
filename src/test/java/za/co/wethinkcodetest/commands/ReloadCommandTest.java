package za.co.wethinkcodetest.commands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.ReloadCommand;
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

public class ReloadCommandTest {

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
    public void testReloadCommandSuccessful() {
        GsonRequest request = new GsonRequest(robot, "reload", new ArrayList<>());
        ReloadCommand reloadCommand = new ReloadCommand(request);

        GsonResponse response = reloadCommand.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("Completed reloading", response.getData().get("Message"));
        assertEquals(robot.getPosition().toString(), response.getState().get("POSITION"));
        assertEquals(robot.getCurrentDirection().toString(), response.getState().get("CURRENT DIRECTION"));
        assertEquals(robot.getName(), response.getState().get("NAME"));
        assertEquals(String.valueOf(robot.getRobotBullet().getBulletShootCapacity()), response.getState().get("BULLET COUNT"));
        assertEquals(String.valueOf(robot.getRobotShield().getNrOfShield()), response.getState().get("SHIELD COUNT"));
    }

    @Test
    public void testReloadCommandUnlaunchedRobot() {
        GsonRequest request = new GsonRequest(null, "reload", new ArrayList<>());
        ReloadCommand reloadCommand = new ReloadCommand(request);

        GsonResponse response = reloadCommand.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        assertTrue(response.getData().containsKey("Message"));
        assertEquals("You first need to launch a robot!!", response.getData().get("Message"));
    }
}
