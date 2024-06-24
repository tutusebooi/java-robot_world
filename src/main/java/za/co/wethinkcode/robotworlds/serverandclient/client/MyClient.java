package za.co.wethinkcode.robotworlds.serverandclient.client;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.gson.GsonSerialization;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * This class represents a client that connects to the server,
 * sends commands, and processes responses.
 */
public class MyClient implements Runnable {
    private static Scanner scanner = new Scanner(System.in);
    static String SERVER_ADDRESS;
    final int SERVER_PORT = 4000;
    static List<RobotInfo> robotInfoList = new ArrayList<>();
    static List<String> validRobotTypes = Arrays.asList("sniper", "midrange", "robot", "shotgun");

    /**
     * Constructs a new MyClient instance with the specified server IP address.
     *
     * @param serverIP The IP address of the server.
     */
    public MyClient(String serverIP) {
        SERVER_ADDRESS = serverIP;
    }

    /**
     * This method runs the client thread.
     * It connects to the server, sends commands, and handles responses.
     */
    @Override
    public void run() {

        AbstractRobot robot = null;
        GsonRequest gsonRequest;
        String instruction;

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            // Initialize writer and reader for server communication
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            boolean runGame = true;
            while (runGame) {

                boolean preGame = true;
                while (preGame) {
                    if (robotInfoList.isEmpty()) {
                        System.out.println("You need to create at least one bot to join the game");
                        storeRobotInfo();
                    }
                    System.out.println("Please enter command:\n - launch : start game\n " +
                            "- show robots: to show robots created\n " +
                            "- add robots: to add create more robots\n " +
                            "- quit : end the program");
                    instruction = getInputFromUser("Enter command: ").strip().toLowerCase();
                    switch (instruction) {
                        case "launch":
                            preGame = false;
                            continue;
                        case "show robots":
                            showStoredRobots();
                            break;
                        case "add robots":
                            storeRobotInfo();
                            break;
                        case "quit":
                            System.out.println("Program shutting down.");
                            preGame = false;
                            runGame = false;
                            continue;
                        default:
                            System.out.println("Not a valid input");
                    }
                }

                boolean shouldContinue = true;
                while (shouldContinue & runGame) {
                    // Get command input from the user
                    if (robot == null) {
                        instruction = launchRobot().strip().toLowerCase();
                    } else {
                        instruction = getInputFromUser(robot.getName() + robot.getPosition() + " (" + robot.getCurrentDirection() + ")" + " > Enter Command: ").strip().toLowerCase();
                    }

                    ArrayList<String> argument = new ArrayList<>(Arrays.asList(instruction.toLowerCase().trim().split(" ")));
                    String getCommand = argument.get(0);
                    argument.remove(0);
                    gsonRequest = new GsonRequest(robot, getCommand, argument);
                    String requestString = GsonSerialization.fromGsonRequestTOJson(gsonRequest);
                    writer.println(requestString);
                    writer.flush();  // Ensure data is sent

                    // Read the response from the server
                    String responseJson = reader.readLine();
                    if (responseJson == null) {
                        shouldContinue = false;
                        continue;
                    }

                    try {
                        // Deserialize the response JSON string to an object
                        GsonResponse gsonResponse = GsonSerialization.fromJsonToGsonResponse(responseJson);
                        robot = gsonResponse.getRobot();
                        String data = (String) gsonResponse.getData().get("Message");

                        if (robot == null) {
                            System.out.println(gsonResponse.getData().get("Message"));
                            robot = gsonResponse.getRobot();
                            shouldContinue = gsonResponse.isStillInPlay();
                            continue;
                        }

                        System.out.println(data);
                        shouldContinue = gsonResponse.isStillInPlay();
                        if (!shouldContinue) {
                            robot = null;
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                }
            }


        } catch (IOException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        }
    }

    /**
     * Prompts the user for input and returns the input string.
     *
     * @param inputPrompt The prompt message displayed to the user.
     * @return The input string entered by the user.
     */
    public static String getInputFromUser(String inputPrompt) {
        System.out.println(inputPrompt);
        String input = scanner.nextLine();

        // Ensure the input is not blank
        while (input.isBlank()) {
            System.out.println(inputPrompt);
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Prompts the user to input robot information and stores it in the list.
     */
    private static void storeRobotInfo() {
        String type;
        do {
            System.out.println("Valid types: " + validRobotTypes);
            type = getInputFromUser("Enter robot type: ").strip().toLowerCase();;
            if (!validRobotTypes.contains(type.toLowerCase())) {
                System.out.println("Invalid robot type.");
            }
        } while (!validRobotTypes.contains(type.toLowerCase()));

        String name = getInputFromUser("Enter robot name: ").strip().toLowerCase();;

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

    /**
     * Prompts the user to select a robot from the stored list to launch.
     *
     * @return The launch command string for the selected robot.
     */
    private static String launchRobot() {
        int robotNumber;
        while (true) {
            showStoredRobots();
            robotNumber = Integer.parseInt(getInputFromUser("Enter the number of the robot to launch: "));

            if (robotNumber < 1 || robotNumber > robotInfoList.size()) {
                System.out.println("Invalid robot number.");
            } else {
                break;
            }
        }
        RobotInfo selectedRobot = robotInfoList.get(robotNumber - 1);
        robotInfoList.remove(selectedRobot);
        return "launch " + selectedRobot.getType() + " " + selectedRobot.getName();
    }
}
