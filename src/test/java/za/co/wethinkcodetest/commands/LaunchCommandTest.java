package za.co.wethinkcodetest.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Commands;
import za.co.wethinkcode.robotworlds.commands.LaunchCommand;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchCommandTest {

    private World world;

    @BeforeEach
    public void setUp() {
        world = new World(); // Initialize the world for each test case
    }

    @Test
    public void testLaunchCommandSuccess() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("robot"); // Robot type
        arguments.add("TestRobot"); // Robot name

        GsonRequest request = new GsonRequest(null, "launch", arguments);
        LaunchCommand launchCommand = new LaunchCommand(request);

        GsonResponse response = launchCommand.executeCommand(world);

        assertEquals("OK", response.getResult());
        assertTrue(response.getData().containsKey("NAME"));
        assertEquals("TestRobot", response.getData().get("NAME"));
        assertEquals("Default Shooter Robot", response.getData().get("TYPE OF ROBOT"));
        assertTrue(response.getData().containsKey("POSITION"));
        assertTrue(response.getData().containsKey("CURRENT DIRECTION"));
        assertTrue(response.getData().containsKey("BULLET COUNT"));
        assertTrue(response.getData().containsKey("SHIELD COUNT"));
    }

    @Test
    public void testLaunchCommandDifferentRobotTypes() {
        ArrayList<String> arguments1 = new ArrayList<>();
        arguments1.add("sniper"); // SniperRobot type
        arguments1.add("SniperBot");

        GsonRequest request1 = new GsonRequest(null, "launch", arguments1);
        LaunchCommand launchCommand1 = new LaunchCommand(request1);

        GsonResponse response1 = launchCommand1.executeCommand(world);
        assertEquals("OK", response1.getResult());
        assertEquals("SniperBot", response1.getData().get("NAME"));
        assertEquals("Sniper Shooter Robot", response1.getData().get("TYPE OF ROBOT"));

        ArrayList<String> arguments2 = new ArrayList<>();
        arguments2.add("midrange"); // MidRangeRobot type
        arguments2.add("MidRangeBot");

        GsonRequest request2 = new GsonRequest(null, "launch", arguments2);
        LaunchCommand launchCommand2 = new LaunchCommand(request2);

        GsonResponse response2 = launchCommand2.executeCommand(world);
        assertEquals("OK", response2.getResult());
        assertEquals("MidRangeBot", response2.getData().get("NAME"));
        assertEquals("MidRange Shooter Robot", response2.getData().get("TYPE OF ROBOT"));
    }
}
