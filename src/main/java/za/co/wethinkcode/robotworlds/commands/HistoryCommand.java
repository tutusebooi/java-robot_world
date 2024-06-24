package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.HashMap;

/**
 * The history command is a command that displays all the commands the robot has executed.
 */
public class HistoryCommand extends Commands {

    public HistoryCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

    @Override
    public GsonResponse executeCommand(World world) {
        GsonResponse gsonResponse;
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> state = new HashMap<>();
        // If robot has not been launched then it will return an error.
        if (getGsonRequest().getGSonRobot() == null) {
            data.put("Message", "You first need to launch a robot!!");
            gsonResponse = new GsonResponse("ERROR", data, state, true, null);
            return gsonResponse;
        }
        AbstractRobot robot = getGsonRequest().getGSonRobot();
        robot.setStatus(getCommandsArrayList().toString());

        data.put("History", getCommandsArrayList().toString());
        data.put("History Count", String.valueOf(getCommandsArrayList().size()));
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
