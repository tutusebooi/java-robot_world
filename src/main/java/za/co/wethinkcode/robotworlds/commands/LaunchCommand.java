package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.robot.*;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The launch command is a command that allows user/client to launch a robot into the world. User/client can specify what their robot's type.
 */
public class LaunchCommand extends Commands{

    public LaunchCommand(GsonRequest gsonRequest) {
        super(gsonRequest);
    }

    @Override
    public GsonResponse executeCommand(World world) {
        GsonResponse gsonResponse;
        HashMap<String, String> data = new HashMap<String, String>();
        HashMap<String, String> state = new HashMap<String, String>();
        // Get robot and argument from gsonRequest don't need to get command since it is already executing
        AbstractRobot robot = getGsonRequest().getGSonRobot();
        ArrayList argument = getGsonRequest().getGsonArgument();

        // Checks if the argument is either empty or null, that means user/client did not enter a name for the robot. Checks if the robot has already been launched.
        if (argument.isEmpty() || argument == null) {
            data.put("Message", "You first need a name for your robot!!");
            gsonResponse = new GsonResponse("ERROR", data, state, true, robot);
            return gsonResponse;
        } else if ((robot != null)) {
            data.put("Message", "You have already launched robot!");
            state.put("NAME", robot.getName());
            state.put("POSITION", robot.getPosition().toString());
            state.put("CURRENT DIRECTION", robot.getCurrentDirection().toString());
            state.put("BULLET COUNT", String.valueOf(robot.getRobotBullet().getBulletShootCapacity()));
            state.put("SHIELD COUNT", String.valueOf(robot.getRobotShield().getNrOfShield()));
            gsonResponse = new GsonResponse("ERROR", data, state, true, robot);
            return gsonResponse;
        } else if (World.getConfiguredPositions().isEmpty()) {
            data.put("Message", "The server is currently not taking in any players right now!!");
            gsonResponse = new GsonResponse("ERROR", data, state, false, robot);
            return gsonResponse;
        }

        // Creates robot and launches it into the world
        try {
            robot = createRobot(argument);
        } catch (IllegalArgumentException illegalArgumentException) {
            data.put("Message", illegalArgumentException.getMessage());
            gsonResponse = new GsonResponse("ERROR", data, state, true, robot);
            return gsonResponse;
        }

        world.addRobotToWorld(robot);

        String message;
        message = String.format("Robot Name: %s%nRobot Type: %s%nRobot Last Status: %s%nRobot Current Position: %s%nRobot Current Direction: %s%nRobot Bullet: %s%nRobot Shield: %s", robot.getName(), robot.getTypeOfRobot(), robot.getStatus(), robot.getPosition(), robot.getCurrentDirection(), robot.getRobotBullet(), robot.getRobotShield());
        data.put("Message", message);

        data.put("NAME", robot.getName());
        data.put("TYPE OF ROBOT", robot.getTypeOfRobot());
        data.put("POSITION", robot.getPosition().toString());
        data.put("CURRENT DIRECTION", robot.getCurrentDirection().toString());
        data.put("BULLET COUNT", String.valueOf(robot.getRobotBullet().getBulletShootCapacity()));
        data.put("SHIELD COUNT", String.valueOf(robot.getRobotShield().getNrOfShield()));

        state.put("NAME", robot.getName());
        state.put("TYPE OF ROBOT", robot.getTypeOfRobot());
        state.put("POSITION", robot.getPosition().toString());
        state.put("CURRENT DIRECTION", robot.getCurrentDirection().toString());
        state.put("BULLET COUNT", String.valueOf(robot.getRobotBullet().getBulletShootCapacity()));
        state.put("SHIELD COUNT", String.valueOf(robot.getRobotShield().getNrOfShield()));

        gsonResponse = new GsonResponse("OK", data, state, true, robot);

        return gsonResponse;
    }

    private AbstractRobot createRobot(ArrayList<String> argumentsPassed) {
        if (argumentsPassed.size() == 2) {
            return switch (argumentsPassed.get(0)) {
                case "sniper", "snipershooter" -> new SniperRobot(argumentsPassed.get(1));
                case "robot", "robotshooter" -> new Robot(argumentsPassed.get(1));
                case "midrange", "midrangeshooter" -> new MidRangeRobot(argumentsPassed.get(1));
                case "shootgun", "shotgun", "shootgunshooter" -> new ShootgunRobot(argumentsPassed.get(1));
                default -> throw new IllegalArgumentException("Please choose correct type of robot!!");
            };
        } else if (argumentsPassed.size() == 1) {
            return switch (argumentsPassed.get(0)) {
                case "sniper", "snipershooter", "robot", "robotshooter", "midrange", "midrangeshooter", "shootgun",
                     "shotgun", "shootgunshooter" -> throw new IllegalArgumentException("You have to state a name when launching robot!!");
                default -> new Robot(argumentsPassed.get(0));
            };
        }
        return null;
    }
}
