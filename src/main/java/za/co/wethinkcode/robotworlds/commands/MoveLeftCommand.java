package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.HashMap;

/**
 * The left command changes the direction of the robot that it is currently facing. It turns anti-clockwise.
 */
public class MoveLeftCommand extends Commands {

    public MoveLeftCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

    @Override
    public GsonResponse executeCommand(World world) {
        GsonResponse gsonResponse;
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> state = new HashMap<>();
        // Get robot and argument from gsonRequest don't need to get command since it is already executing
        AbstractRobot robot = getGsonRequest().getGSonRobot();

        // If robot has not been launched then it will return an error.
        if (robot == null) {
            data.put("Message", "You first need to launch a robot!!");
            gsonResponse = new GsonResponse("ERROR", data, state, true, robot);
            return gsonResponse;
        }

        // Updates the direction the robot is facing by turning left.
        Direction currentDirection = robot.getCurrentDirection();
        int index = Direction.valueOf(currentDirection.toString()).ordinal();
        index--;
        if (index < 0){
            currentDirection = Direction.WEST;
        } else {
            currentDirection = Direction.values()[index];
        }
        robot.setCurrentDirection(currentDirection);
        robot.setStatus("Turned left.");

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
