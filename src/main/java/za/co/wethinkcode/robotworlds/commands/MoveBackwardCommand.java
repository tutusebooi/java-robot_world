package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The backward command is a command that moves the robot back for a certain distance and checks if the new updated position is allowed.
 */
public class MoveBackwardCommand extends Commands {

    public MoveBackwardCommand(GsonRequest gsonRequest) {
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

        // Updates position if the robot is outside the world, hits obstacle or went over obstacle it will end its session.
        state.put("NAME", robot.getName());
        state.put("TYPE OF ROBOT", robot.getTypeOfRobot());
        state.put("POSITION", robot.getPosition().toString());
        state.put("CURRENT DIRECTION", robot.getCurrentDirection().toString());
        state.put("BULLET COUNT", String.valueOf(robot.getRobotBullet().getBulletShootCapacity()));
        state.put("SHIELD COUNT", String.valueOf(robot.getRobotShield().getNrOfShield()));

        int numberOfSteps = Integer.parseInt(argument.get(0));
        Position oldPosition = robot.getPosition();
        if (world.updatePosition(-numberOfSteps, robot)) {
            if (!oldPosition.equals(robot.getPosition())) {
                robot.setStatus("Moved backward by " + numberOfSteps + " steps.");
            }
        } else {
            data.put("Message", robot.getStatus());
            gsonResponse = new GsonResponse("ERROR", data, state, false, robot);
            return gsonResponse;
        }

        data.put("Message", robot.getStatus());

        state.put("POSITION", robot.getPosition().toString());

        gsonResponse = new GsonResponse("OK", data, state, true, robot);

        return gsonResponse;
    }
}
