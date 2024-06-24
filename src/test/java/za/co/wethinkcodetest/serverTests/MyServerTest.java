package za.co.wethinkcodetest.serverTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import za.co.wethinkcode.robotworlds.serverandclient.server.MyServer;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class MyServerTest {
    private MyServer server;

    @Before
    public void setUp() {
        server = new MyServer(new World()); // Initialize server with a mock World
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    @After
    public void tearDown() {
        server.stop(); // Stop the server after each test
    }

    @Test
    public void testServerConnectionHandling() {
        try {
            // Simulate client connections (replace with actual socket operations as needed)
            Socket client1 = new Socket("localhost", MyServer.PORT);
            Socket client2 = new Socket("localhost", MyServer.PORT);

            // Wait for clients to connect and check if they are added to the server's client list
            Thread.sleep(1000); // Adjust sleep time based on your system's speed and test needs

            assertEquals(2, MyServer.clients.size()); // Assert that both clients are connected

            client1.close();
            client2.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
