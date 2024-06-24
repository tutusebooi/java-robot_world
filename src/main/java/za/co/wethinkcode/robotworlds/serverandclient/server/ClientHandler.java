package za.co.wethinkcode.robotworlds.serverandclient.server;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.gson.GsonSerialization;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.commands.Commands;

/**
 * This class handles communication with a client.
 * It reads requests from the client, processes them, and sends back responses.
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    public BufferedReader reader;
    private World world;
    public PrintWriter writer;
    private AbstractRobot robot = null;

    /**
     * Constructor for ClientHandler.
     *
     * @param socket The client socket.
     * @param world  The world instance.
     * @throws IOException If an I/O error occurs when creating the input or output streams.
     */
    public ClientHandler(Socket socket, World world) throws IOException {
        this.clientSocket = socket;
        this.world = world;
        System.out.println("New client connected");
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    /**
     * This method runs the client handler thread.
     * It listens for client requests, processes them, and sends back responses.
     */
    @Override
    public void run() {
        try {
            boolean shouldContinue = true;

            while (shouldContinue) {
                // Read request from client
                String requestString = reader.readLine();

                if (requestString == null || requestString.trim().isEmpty()) {
                    System.out.println("Client disconnected");
                    shouldContinue = false;
                    continue;
                }

                try {
                    // Deserialize request and handle command
                    GsonRequest gsonRequest = GsonSerialization.fromJsonToGsonRequest(requestString);
                    Commands command = Commands.createCommandGson(gsonRequest);

                    // Serialize response and send it to the client
                    Commands.addCommandsArrayList(command);
                    GsonResponse gsonResponse = world.handleCommands(command);

                    // Check if the client should continue to play
                    shouldContinue = gsonResponse.isStillInPlay();
                    if (!shouldContinue) {
                        robot = null;
                        shouldContinue = true;
                    }
                    // The server would send the result of the gsonRequest to the gsonResponse
                    String responseString = GsonSerialization.fromGsonResponseTOJson(gsonResponse);
                    writer.println(responseString);
                    writer.flush();  // Ensure data is sent
                } catch (Exception e) {
                    writer.println("Command can not be executed");
                }
            }
        } catch (IOException e) {
            System.out.println("Client connection error: " + e.getMessage());
        } finally {
            // Clean up resources and remove client
            removeClient();
            world.removeRobotFromWorld(robot);
            stopClientHandler();
        }
    }

    /**
     * Removes the client from the server's client list and closes the client socket.
     */
    public void removeClient() {
        synchronized (MyServer.clients) {
            MyServer.clients.remove(clientSocket);
            MyServer.clientCount--;
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    /**
     * Stops the client handler by closing the input and output streams.
     */
    public void stopClientHandler() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ex) {
            System.out.println("Error closing streams: " + ex.getMessage());
        }
    }
}
