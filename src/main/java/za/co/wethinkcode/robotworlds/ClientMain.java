package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.serverandclient.client.MyClient;

import java.util.Scanner;

/**
 * The main class for the client application.
 * This class starts the client communication in a separate thread.
 */
public class ClientMain {

    /**
     * The entry point of the client application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverIP;

        // Prompt the user to enter the server IP address
        System.out.println("Please enter Server IP address (if server and client is run locally then just press enter)");
        serverIP = scanner.nextLine();

        // If no IP address is provided, default to "localhost"
        if (serverIP.isEmpty()) {
            serverIP = "localhost";
        }

        // Create a new thread to handle client communication and start it
        new Thread(new MyClient(serverIP)).start();
    }
}
