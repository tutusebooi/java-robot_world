package za.co.wethinkcodetest.serverTests;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ServerMain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
public class ServerMainTest {
    @Test
    public void testServerThreadStarting() throws Exception {
        // Redirect standard input and output for testing
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        String userInput = "quit\n"; // Simulate user input of "quit"

        try {
            // Set up the input stream to simulate user input
            System.setIn(new ByteArrayInputStream(userInput.getBytes()));

            // Create a ByteArrayOutputStream to capture the output
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            // Create a thread to run the server
            Thread serverThread = new Thread(() -> ServerMain.runServer(new Scanner(System.in)));
            serverThread.start();

            // Allow some time for the server thread to start and process the command
            Thread.sleep(100);

            // Verify if the server thread is not alive (indicating it has stopped)
            assertFalse(serverThread.isAlive());

        } finally {
            // Restore standard input and output
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}