package za.co.wethinkcode.robotworlds.serverandclient.server;

import za.co.wethinkcode.robotworlds.world.World;
//import za.co.wethinkcode.robotworlds.robot.Robot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * MyServer is responsible for managing client connections and handling requests in the robot worlds application.
 * It implements the Runnable interface to allow running the server in a separate thread.
 */
public class MyServer implements Runnable {
    public static final int PORT = 4000; // The port on which the server will listen for connections
    public static int clientCount = 0; // Keeps track of the number of connected clients
    private ServerSocket serverSocket = null; // The server socket for accepting connections
    private Socket clientSocket = null; // The socket for communicating with a connected client
    public static volatile boolean running; // Flag to control the server's running state
    private Thread serverThread; // The thread in which the server will run
    public static ArrayList<Socket> clients = new ArrayList<>(); // List of connected client sockets

    private Thread clientThread; // The thread for handling a client connection

    private World world; // The world in which robots operate

    /**
     * Constructor initializes the world.
     */
    public MyServer() {
        this.world = new World();
    }
    public MyServer(World world) {
        this.world = world;
    }

    /**
     * The run method contains the main server loop which accepts client connections
     * and starts a new thread for each connected client.
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT); // Create the server socket
            running = true; // Set the running flag to true
            while (running) {
                while (clientCount < 5 && running) { // Allow up to 5 clients to connect
                    try {
                        clientSocket = serverSocket.accept(); // Accept a new client connection
                        clientCount++; // Increment the client count
                        clients.add(clientSocket); // Add the client socket to the list

                        // Create a new ClientHandler and start a new thread for the client
                        ClientHandler clientHandler = new ClientHandler(clientSocket, world);
                        clientThread = new Thread(clientHandler);
                        clientThread.start();

                    } catch (IOException e) {
                        System.out.println("Connection Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Ensure that the server and client sockets are closed when the server stops
            try {
                System.out.println("Server Shutting down.");
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error When closing sockets: " + e.getMessage());
            }
        }
    }

    /**
     * Stops the server by setting the running flag to false and closing the server and client sockets.
     */
    public void stop() {
        running = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error When closing serverSocket: " + e.getMessage());
            }
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error When closing clientSocket: " + e.getMessage());
            }
        }
        if (serverThread != null) {
            serverThread.interrupt();
        }
    }
}

