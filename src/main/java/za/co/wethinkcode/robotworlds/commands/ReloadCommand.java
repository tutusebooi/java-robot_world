package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * The reload command is a command that reloads the robot's bullets to its maximum capacity.
 */
public class ReloadCommand extends Commands {

    public ReloadCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

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

        // Restores the bullets lost or destroyed.
        robot.restoreBullet();
        robot.setStatus("Completed reloading");

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
