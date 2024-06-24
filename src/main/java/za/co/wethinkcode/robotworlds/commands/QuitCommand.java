package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.HashMap;

/**
 * The quit command is a command that makes the robot leave the world and quit the game.
 */
public class QuitCommand extends Commands {

    public QuitCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

    @Override
    public GsonResponse executeCommand(World world) {
        GsonResponse gsonResponse;
        HashMap<String, String> data = new HashMap<String, String>();
        HashMap<String, String> state = new HashMap<String, String>();
        // Get robot and argument from gsonRequest don't need to get command since it is already executing
        AbstractRobot robot = getGsonRequest().getGSonRobot();

        // Checks if the robot has been launched.
        if (getGsonRequest().getGSonRobot() == null) {
            data.put("Message", "You first need to create a robot!!");
            gsonResponse = new GsonResponse("ERROR", data, state, true, robot);
            return gsonResponse;
        }

        // Switches off the robot and removes it from world.
        //robot.setStatus("Ending session...");
        //world.removeRobotFromWorld(robot);

        data.put("Message", "Ending session...");

        state.put("NAME", robot.getName());
        state.put("TYPE OF ROBOT", robot.getTypeOfRobot());
        state.put("POSITION", robot.getPosition().toString());
        state.put("CURRENT DIRECTION", robot.getCurrentDirection().toString());
        state.put("BULLET COUNT", String.valueOf(robot.getRobotBullet().getBulletShootCapacity()));
        state.put("SHIELD COUNT", String.valueOf(robot.getRobotShield().getNrOfShield()));

        gsonResponse = new GsonResponse("OK", data, state, false, robot);

        return gsonResponse;
    }
}
