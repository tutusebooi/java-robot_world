package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.HashMap;

/**
 * The Help Command displays all the commands the robot can process and execute.
 */
public class HelpCommand extends Commands {

    public HelpCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

    @Override
    public GsonResponse executeCommand(World world) {
        GsonResponse gsonResponse;
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> state = new HashMap<>();
        AbstractRobot robot = getGsonRequest().getGSonRobot();
        // If robot has not been launched then it will return an error.
        if (getGsonRequest().getGSonRobot() == null) {
            data.put("Message", "You first need to launch a robot!!");
            gsonResponse = new GsonResponse("ERROR", data, state, true, null);
            return gsonResponse;
        }

        // Shows all the commands that the robot can do.
        robot.setStatus("I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK - move backward by specified number of steps, e.g. 'BACK 10'\n" +
                "RIGHT - turn right by 90 degrees\n" +
                "LEFT - turn left by 90 degrees\n" +
                "HISTORY - shows the history of all the movement commands played" +
                "Shoot - Robot takes a shoot from it's gun\n" +
                "repair - Repairs the robot's shield\n" +
                "reload - Reloads the robot's gun with new ammunation.");

        data.put("Commands", "I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK - move backward by specified number of steps, e.g. 'BACK 10'\n" +
                "RIGHT - turn right by 90 degrees\n" +
                "LEFT - turn left by 90 degrees\n" +
                "HISTORY - shows the history of all the movement commands played" +
                "Shoot - Robot takes a shoot from it's gun\n" +
                "repair - Repairs the robot's shield\n" +
                "reload - Reloads the robot's gun with new ammunation.");

        data.put("Message", robot.getStatus());

        state.put("NAME", robot.getName());
        state.put("TYPE OF ROBOT", robot.getTypeOfRobot());
        state.put("POSITION", robot.getPosition().toString());
        state.put("CURRENT DIRECTION", robot.getCurrentDirection().toString());
        state.put("BULLET COUNT", String.valueOf(robot.getRobotBullet().getBulletShootCapacity()));
        state.put("SHIELD COUNT", String.valueOf(robot.getRobotShield().getNrOfShield()));
        gsonResponse = new GsonResponse("OK", data, state, true, robot);

        return gsonResponse;
    }
}
