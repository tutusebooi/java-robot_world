package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The look command is a command that looks in the sight of the robot NORTH, EAST, SOUTH, WEST and checks for all obstacles and robots.
 */
public class LookCommand extends Commands {

    public LookCommand(GsonRequest gsonRequest) {
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

        // Looks for any objects or robots in its line of sight.
        ArrayList<String> obstaclesAndRobotList = robot.executeLookCommand(world);

        if (robot.getStatus().isEmpty() || obstaclesAndRobotList == null) {
            data.put("Message", "No obstacles or robots in your line of vision!!");
        } else {
            StringBuilder message = new StringBuilder("The number of obstacles/robots in your line of vision is: " + obstaclesAndRobotList.size() + "\n");
            int i = 1;
            for (String objects: obstaclesAndRobotList) {
                message.append(i).append(". ").append(objects).append("\n");
                i++;
            }

            data.put("Message", message.toString());
        }

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
