package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

/**
 * This is the commands class where all commands are created, stored, processed and executed. This is an abstract class for different commands to be extended.
 */
public abstract class Commands {
    private GsonRequest gsonRequest;
    public static ArrayList<Commands> commandsArrayList = new ArrayList<>();

    /**
     * This function executes the command in World.java it takes in the world and uses it to execute commands robot wants to do.
     * @param world This is the world parameter where the world is taken to account, for other robots, obstacles, etc.
     * @return The function returns a Gson formatted response to know if command executed or failed.
     */
    public abstract GsonResponse executeCommand(World world);

    /**
     * The commands take in the user's command that it wants the robot to perform.
     * @param gsonRequest The gsonRequest sores the user's command, robot & argument.
     */
    protected Commands(GsonRequest gsonRequest) {
        this.gsonRequest = gsonRequest;
    }

    /**
     * This function returns the gsonRequest.
     * @return The request made from the user/client.
     */
    public GsonRequest getGsonRequest() {
        return gsonRequest;
    }

    /**
     * This creates the command by getting the command name from the Request sent to it by the user/client through the server. Then process it and returns the command the user/client wanted or if no command is named what the user/client specified then it will throw an illegal argument.
     * @param command This is where the user's request is sent.
     * @return it returns the class of the command created.
     */
    public static Commands createCommandGson(GsonRequest command) {
        return switch (command.getGsonCommand()) {
            case "shutdown", "quit", "off" -> new QuitCommand(command);
            case "help" -> new HelpCommand(command);
            case "forward" -> new MoveForwardCommand(command);
            case "back", "backward" -> new MoveBackwardCommand(command);
            case "left" -> new MoveLeftCommand(command);
            case "right" -> new MoveRightCommand(command);
            case "history" -> new HistoryCommand(command);
            case "look" -> new LookCommand(command);
            case "state", "status" -> new StateCommand(command);
            case "launch" -> new LaunchCommand(command);
            case "shoot", "fire" -> new ShootCommand(command);
            case "reload" -> new ReloadCommand(command);
            case "repair" -> new RepairCommand(command);
            default -> throw new IllegalArgumentException("Unsupported command: " + command);
        };
    }

    /**
     * Adds the command to the history of commands created and executed by the robot.
     * @param command The command class that was used to perform an instruction.
     */
    public static void addCommandsArrayList(Commands command) {
        Commands.commandsArrayList.add(command);
    }

    /**
     * This function returns all the history of the commands entered, processed and executed by the robot.
     * @return The array list of the commands in commandsArrayList.
     */
    public static ArrayList<Commands> getCommandsArrayList() {
        return commandsArrayList;
    }
}
