package za.co.wethinkcodetest.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.commands.Commands;
import za.co.wethinkcode.robotworlds.commands.HelpCommand;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandTest {

    @Test
    public void testHelpCommandCreation() {
        ArrayList<String> arguments = new ArrayList<>();
        AbstractRobot robot = new Robot("TestRobot");
        GsonRequest request = new GsonRequest(robot, "help", arguments);
        Commands command = Commands.createCommandGson(request);
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void testHelpCommandExecution() {
        ArrayList<String> arguments = new ArrayList<>();
        AbstractRobot robot = new Robot("TestRobot");
        robot.setPosition(new Position(0, 0)); // Ensure the robot has a valid position
        GsonRequest request = new GsonRequest(robot, "help", arguments);
        Commands command = Commands.createCommandGson(request);
        World world = new World(); // Assuming a default constructor exists

        GsonResponse response = command.executeCommand(world);

        assertEquals("OK", response.getResult());
        Map<String, String> data = response.getData();  // Assuming getData() returns a Map<String, String>
        assertTrue(data.get("Commands").contains("HELP"));
        assertEquals("I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK - move backward by specified number of steps, e.g. 'BACK 10'\n" +
                "RIGHT - turn right by 90 degrees\n" +
                "LEFT - turn left by 90 degrees\n" +
                "HISTORY - shows the history of all the movement commands played.", robot.getStatus());
    }

    @Test
    public void testHelpCommandExecutionWithoutRobot() {
        ArrayList<String> arguments = new ArrayList<>();
        GsonRequest request = new GsonRequest(null, "help", arguments);
        Commands command = Commands.createCommandGson(request);
        World world = new World();

        GsonResponse response = command.executeCommand(world);

        assertEquals("ERROR", response.getResult());
        Map<String, String> data = response.getData();
        assertEquals("You first need to launch a robot!!", data.get("Message"));
    }
}
