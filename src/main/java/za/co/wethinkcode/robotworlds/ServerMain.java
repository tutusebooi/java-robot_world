package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.serverandclient.server.MyServer;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.obstacle.AbstractObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The ServerMain class is the entry point for starting the server and
 * handling user commands from the console.
 */
public class ServerMain {
    private static List<AbstractObstacle> obstacles;
    private static ArrayList<AbstractRobot> robots;
    /**
     * The main method which serves as the entry point for the server application.
     * It initializes the scanner and runs the server.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize the Scanner to read commands from the console
        Scanner scanner = new Scanner(System.in);
        // Start the server and process commands
        runServer(scanner);
    }

    /**
     * Runs the server and processes commands from the provided Scanner.
     * It starts the server in a new thread and listens for user commands.
     *
     * @param scanner Scanner to read commands from.
     */
    public static void runServer(Scanner scanner) {
        // Create and start the server thread
        MyServer server = new MyServer();
        Thread serverThread = new Thread(server);
        serverThread.start();

        String command;
        boolean running = true;
        while (running) {
            // Prompt the user to enter a command
            System.out.println("Enter command");

            // Read the command from the console and convert it to lowercase
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "dump":
                    // Print all robots and obstacles
                    dump();
                    break;
                case "robots":
                    // Print all robots
                    printRobots();
                    break;
                case "quit":
                    // Stop the server and exit the loop
                    server.stop();
                    running = false;
                    break;
                default:
                    // Handle invalid commands
                    System.out.println("Invalid command.");
            }
        }
        // Indicate that the program is shutting down
        System.out.println("Program Shutting down");
    }
    /**
     * Prints all obstacles currently in the world.
     */
    public static void printObstacles() {
        // Retrieve the list of obstacles from the world
        obstacles = World.getObstacles();
        if (obstacles.isEmpty()){
            System.out.println("No obstacles present.");
        } else {
            // Print the obstacles
            System.out.println("Obstacles:");
            System.out.println(World.getObstacles());
        }
    }

    /**
     * Prints all robots and obstacles currently in the world.
     */
    public static void dump(){
        printRobots();
        printObstacles();
    }
    public static void printRobots(){
        robots = (ArrayList<AbstractRobot>) World.getRobots();
        if (robots.isEmpty()) {
            System.out.println("No robots present");
        } else {
            for (AbstractRobot robot : World.getRobots()) {
                System.out.println(robot.getName());
            }
        }
    }

}

