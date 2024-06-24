
package za.co.wethinkcodetest.robot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.MidRangeRobot;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private MidRangeRobot robot;

    @BeforeEach
    void setUp() {
        robot = new MidRangeRobot("TestRobot");
    }

    @AfterEach
    void tearDown() {
        robot = null;
    }

    @Test
    void getName() {
        assertEquals("TestRobot", robot.getName());
    }

    @Test
    void getPosition() {
        assertNotNull(robot.getPosition());
        assertEquals(new Position(1, 1), robot.getPosition());
    }

    @Test
    void getCurrentDirection() {
        assertNotNull(robot.getCurrentDirection());
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
    }

    @Test
    void getStatus() {
        assertEquals("Ready", robot.getStatus());
    }

    @Test
    void setCurrentDirection() {
        robot.setCurrentDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, robot.getCurrentDirection());
    }

    @Test
    void setStatus() {
        robot.setStatus("Active");
        assertEquals("Active", robot.getStatus());
    }

    @Test
    void setPosition() {
        Position newPosition = new Position(5, 5);
        robot.setPosition(newPosition);
        assertEquals(newPosition, robot.getPosition());
    }

    @Test
    void testToString() {
        String expectedString = "[1,1] Current Direction(NORTH) TestRobot > Ready";
        assertEquals(expectedString, robot.toString());
    }
}
