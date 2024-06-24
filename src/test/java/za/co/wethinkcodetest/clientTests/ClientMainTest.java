package za.co.wethinkcodetest.clientTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.serverandclient.client.MyClient;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMainTest {
    private Thread clientThread;

    @BeforeEach
    public void setUp() {
        // Create a new thread for the client
        String ServerIP = "localhost";
        clientThread = new Thread(new MyClient(ServerIP));
    }

    @Test
    public void testClientThreadStarts() {
        // Verify that the client thread is not alive before starting
        assertFalse(clientThread.isAlive(), "Client thread should not be alive before start.");

        // Start the client thread and verify that it becomes alive
        clientThread.start();
        assertTrue(clientThread.isAlive(), "Client thread should be alive after start.");
    }

    @Test
    public void testClientThreadStops() throws InterruptedException {
        // Start the client thread
        clientThread.start();
        assertTrue(clientThread.isAlive(), "Client thread should be alive after start.");

        // Interrupt the client thread and wait for it to finish
        clientThread.interrupt();
        clientThread.join(); // Wait for the thread to finish

        // Verify that the client thread is no longer alive after interruption
        assertFalse(clientThread.isAlive(), "Client thread should be stopped after interrupt.");
    }
}
