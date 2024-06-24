package za.co.wethinkcodetest.robot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.robot.robot.ShootgunRobot;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import static org.junit.jupiter.api.Assertions.*;

public class ShootgunRobotTest {

    private ShootgunRobot robot;
    private World world;

    @BeforeEach
    public void setup() {
        robot = new ShootgunRobot("ShootgunRobot");
        world = new World();
        robot.setPosition(new Position(0, 0));
        world.addRobotToWorld(robot);
    }

    @Test
    public void testRobotInitialization() {
        assertEquals("ShootgunRobot", robot.getName());
        assertEquals("Ready", robot.getStatus());
        assertEquals(Direction.NORTH, robot.getCurrentDirection());
        assertEquals("Shootgun Shooter Robot", robot.theTypeOfRobotSet());
        assertEquals(3, robot.getRobotBullet().getBulletShootCapacity());
        assertEquals(7, robot.getRobotBullet().getDistanceOfShoot());
        assertEquals(3, robot.getRobotShield().getNrOfShield());
    }

    @Test
    public void testExecuteShootCommand() {
        boolean didShootHit = robot.executeShootCommand(world);
        assertFalse(didShootHit);
        assertEquals("You took a shot and missed!", robot.getStatus());
    }
}

