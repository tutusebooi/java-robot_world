package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.*;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * The repair command is a command that repairs the robot's shield and restores it back to full maximum capacity.
 */
public class RepairCommand extends Commands{

    /**
     * The commands take in the user's command that it wants the robot to perform.
     *
     * @param gsonRequest The gsonRequest sores the user's command, robot & argument.
     */
    public RepairCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

    /**
     * This function executes the command in World.java it takes in the world and uses it to execute commands robot wants to do.
     *
     * @param world This is the world parameter where the world is taken to account, for other robots, obstacles, etc.
     * @return The function returns a Gson formatted response to know if command executed or failed.
     */
    @Override
    public GsonResponse executeCommand(World world) {
        GsonResponse gsonResponse;
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> state = new HashMap<>();
        // Get robot and argument from gsonRequest don't need to get command since it is already executing
        AbstractRobot robot = getGsonRequest().getGSonRobot();
        ArrayList<String> argument = getGsonRequest().getGsonArgument();

        // If robot has not been launched then it will return an error.
        if (robot == null) {
            data.put("Message", "You first need to launch a robot!!");
            gsonResponse = new GsonResponse("ERROR", data, state, true, robot);
            return gsonResponse;
        }

        // time wait and reload
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Restores the shield lost or destroyed.
        robot.restoreShield();
        robot.setStatus("Completed repair");

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
