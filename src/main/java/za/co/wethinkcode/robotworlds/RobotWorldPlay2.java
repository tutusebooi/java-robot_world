package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.commands.Commands;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.gson.GsonSerialization;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.serverandclient.client.RobotInfo;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RobotWorldPlay2 {
    static Scanner scanner = new Scanner(System.in);
    static List<RobotInfo> robotInfoList = new ArrayList<>();
    static List<String> validRobotTypes = Arrays.asList("sniper", "midrange", "robot", "shotgun");

    public static void main(String[] args) {
        // The start when client starts server
        AbstractRobot robot = null;
        World world = new World();
        String instruction;
        Commands command;
        GsonRequest gsonRequest;
        GsonResponse gsonResponse;

        world.printObstacles();
        boolean preGame = true;
        while (preGame) {
            if (robotInfoList.isEmpty()) {
                System.out.println("You need to create at least one bot to join the game");
                storeRobotInfo();
            }
            System.out.println("Please enter command:\n - launch : start game\n " +
                                "- show robots: to show robots created\n " +
                                "- add robots: to add create more robots");
            instruction = getInputFromUser("Enter command: ").strip().toLowerCase();
            switch (instruction) {
                case "launch":
                    preGame = false;
                    continue;
                case "show robots":
                    showStoredRobots();
                    break;
                case "add robots":
                    storeRobotInfo ();
                    break;
                default:
                    System.out.println("Not a valid input");
            }
        }

        boolean shouldContinue = true;
        do {
            System.out.println("Mainloop started");
            if (robot == null) {
                instruction = launchRobot();
            } else {
                instruction = getInputFromUser(robot.getName() + " > Enter Command: ").strip().toLowerCase();
            }
            ArrayList<String> argument = new ArrayList<>(Arrays.asList(instruction.toLowerCase().trim().split(" ")));
            String getCommand = argument.get(0);
            argument.remove(0);
            gsonRequest = new GsonRequest(robot, getCommand, argument);
            // Client would send gsonRequest to server
            try {
                // Server side handling the commands
                command = Commands.createCommandGson(gsonRequest);
                Commands.addCommandsArrayList(command);

                gsonResponse = world.handleCommands(command);
                robot = gsonResponse.getRobot();
                shouldContinue = gsonResponse.isStillInPlay();
                // The server would send the result of the gsonRequest to the gsonResponse
                System.out.println(GsonSerialization.fromGsonResponseTOJson(gsonResponse));
            } catch (IllegalArgumentException e) {
                if (robot != null) {
                    robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
                } else {
                    System.out.println("Sorry, I did not understand '" + instruction + "'.");
                }
            }
        } while (shouldContinue);
    }

    /**
     * Takes input from user.
     * @param inputPrompt The prompt the user is asked for.
     * @return The input the user entered.
     */
    private static String getInputFromUser (String inputPrompt){
        System.out.println(inputPrompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(inputPrompt);
            input = scanner.nextLine();
        }
        return input;
    }
    /**
     * Stores robot information (type and name) based on user input.
     */
    private static void storeRobotInfo() {
        String type;
        do {
            System.out.println("Valid types: " + validRobotTypes);
            type = getInputFromUser("Enter robot type: ");
            if (!validRobotTypes.contains(type.toLowerCase())) {
                System.out.println("Invalid robot type.");
            }
        } while (!validRobotTypes.contains(type.toLowerCase()));

        String name = getInputFromUser("Enter robot name: ");

        RobotInfo robotInfo = new RobotInfo(type, name);
        robotInfoList.add(robotInfo);

        System.out.println("Robot stored");
    }
    /**
     * Displays all stored robots.
     */
    private static void showStoredRobots() {
        if (robotInfoList.isEmpty()) {
            System.out.println("No robots stored.");
        } else {
            System.out.println("Stored robots:");
            int index = 1;
            for (RobotInfo robotInfo : robotInfoList) {
                System.out.println(index + ". " + robotInfo);
                index++;
            }
        }
    }
    private static String launchRobot() {
        int robotNumber;
        while(true){
            showStoredRobots();
            robotNumber = Integer.parseInt(getInputFromUser("Enter the number of the robot to launch: "));

            if (robotNumber < 1 || robotNumber > robotInfoList.size()) {
                System.out.println("Invalid robot number.");
            } else {
                break;
            }
        }
        RobotInfo selectedRobot = robotInfoList.get(robotNumber - 1);
        return "launch "+ selectedRobot.getType() + " "+ selectedRobot.getName();
    }
}

