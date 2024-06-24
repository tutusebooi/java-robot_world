package za.co.wethinkcodetest.world;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the World class, which tests the robot's interaction with obstacles
 * and world boundaries.
 */
class WorldTest {

    private World world;
    private AbstractRobot robot;

    /**
     * Sets up the test environment before each test.
     * Initializes the World and a Robot, and adds the robot to the world.
     */
    @BeforeEach
    void setUp() {
        world = new World();
        robot = new Robot("TestRobot");
        world.addRobotToWorld(robot);
    }

    /**
     * Tests that the robot recognizes an obstacle and does not move into it.
     */
    @Test
    void testRobotRecognizesObstacle() {
        // Place robot at position (89, 90), moving one step east hits the obstacle at (90, 90)
        robot.setPosition(new Position(89, 90));
        robot.setCurrentDirection(Direction.EAST);

        boolean success = world.updatePosition(1, robot);

        // Assert that the robot does not move into the obstacle
        assertFalse(success, "Robot should not move to a position with an obstacle");
        assertEquals("This robot hit an obstacle!", robot.getStatus());
    }

    /**
     * Tests that the robot does not move beyond the boundaries of the world.
     */
    @Test
    void testRobotDoesNotGoBeyondWorldBoundaries() {
        // Place robot at position (200, 0), moving one step east should go out of bounds
        robot.setPosition(new Position(200, 0));
        robot.setCurrentDirection(Direction.EAST);

        boolean success = world.updatePosition(1, robot);

        // Assert that the robot does not move out of the world's boundaries
        assertFalse(success, "Robot should not move beyond the world boundaries");
        assertEquals("This robot is outside of the world!", robot.getStatus());
    }

    /**
     * Tests that the robot can move within the boundaries of the world.
     */
    @Test
    void testRobotMovesWithinWorld() {
        // Place robot at a valid position and move within bounds
        robot.setPosition(new Position(10, 10));
        robot.setCurrentDirection(Direction.NORTH);

        boolean success = world.updatePosition(5, robot);

        // Assert that the robot moves to the correct position within the world boundaries
        assertTrue(success, "Robot should move to a valid position within the world boundaries");
        assertEquals(new Position(10, 15), robot.getPosition());
    }
}
