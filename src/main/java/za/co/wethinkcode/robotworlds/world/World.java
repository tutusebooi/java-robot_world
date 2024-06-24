package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.commands.Commands;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.world.obstacle.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final Position topLeft = new Position(0, 200);
    private final Position bottomRight = new Position(200, 0);
    private static List<AbstractObstacle> obstacles = new ArrayList<>();
    private static ArrayList<AbstractRobot> robots = new ArrayList<>();
    private static ArrayList<Position> configuredPositions = new ArrayList<>();

    public World() {
        Random random = new Random();
        int x = 0;
        int y = 0;
        obstacles.add(new MountainObstacle(90, 90));
        obstacles.add(new LakeObstacle(3,10));
        for (int i = 0; i < 10; i++) {
            x = random.nextInt(198)+3;
            y = random.nextInt(198)+3;
            LakeObstacle lakeObstacle = new LakeObstacle(x, y);
            obstacles.add(lakeObstacle);

            x = random.nextInt(198)+3;
            y = random.nextInt(198)+3;
            SquareObstacle squareObstacle = new SquareObstacle(x, y);
            obstacles.add(squareObstacle);

            x = random.nextInt(198)+3;
            y = random.nextInt(198)+3;
            PitObstacle pitObstacle = new PitObstacle(x, y);
            obstacles.add(pitObstacle);
        };

        configuredPositions.add(new Position(1, 1));
        configuredPositions.add(new Position(199, 199));
        configuredPositions.add(new Position(199, 1));
        configuredPositions.add(new Position(1, 199));
    }

    public void addRobotToWorld(AbstractRobot robot) {
        robots.add(robot);
        robot.setPosition(configuredPositions.get(0));
        configuredPositions.remove(0);
    }

    public boolean updatePosition(int nrSteps, AbstractRobot robot){
        int newX = robot.getPosition().getX();
        int newY = robot.getPosition().getY();

        if (Direction.NORTH.equals(robot.getCurrentDirection())) {
            newY += nrSteps;
        } else if (Direction.SOUTH.equals(robot.getCurrentDirection())) {
            newY -= nrSteps;
        } else if (Direction.EAST.equals(robot.getCurrentDirection())) {
            newX += nrSteps;
        } else if (Direction.WEST.equals(robot.getCurrentDirection())) {
            newX -= nrSteps;
        }

        Position oldPosition = robot.getPosition();
        Position newPosition = new Position(newX, newY);

        if (!newPosition.isIn(this.topLeft, this.bottomRight)) {
            robot.setStatus("This robot is outside of the world!");
            return false;
        }
        for (AbstractRobot robotEnemy: getRobots()) {
            if (!robotEnemy.getName().equals(robot.getName()) && newPosition.isRobotInNewPosition(robotEnemy)) {
                robot.setStatus(robotEnemy.getName() + " robot is in that position!!");
                return true;
            }
        }

        if (isPositionBlockedByObstacle(newPosition)) {
            robot.setStatus("This robot hit an obstacle!");
            return false;
        }

        if (isNewPositionsPathBlocked(oldPosition, newPosition)) {
            robot.setStatus("This robot hit an obstacle while moving!");
            return false;
        }

        robot.setPosition(newPosition);
        return true;
    }

    public static boolean isPositionBlockedByObstacle(Position newPosition) {
        for (AbstractObstacle obstacle: obstacles){
            if (obstacle.blocksPosition(newPosition)){
                return true;
            }
        }
        return false;
    }

    public static boolean isNewPositionsPathBlocked(Position oldPosition, Position newPosition) {
        for (AbstractObstacle obstacle: obstacles){
            if (obstacle.blocksPath(newPosition, oldPosition)){
                return true;
            }
        }
        return false;
    }

    public GsonResponse handleCommands(Commands command) {
        return command.executeCommand(this);
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public static ArrayList<Position> getConfiguredPositions() {
        return configuredPositions;
    }

    public static List<AbstractObstacle> getObstacles() {
        return obstacles;
    }

    public static List<AbstractRobot> getRobots() {
        return robots;
    }

    public void removeRobotFromWorld(AbstractRobot robot) {
        robots.remove(robot);
    }

    public void printObstacles() {
        for (AbstractObstacle obstacle: obstacles) {
            System.out.printf("- %s is at (%d, %d) to (%d, %d). Size: %s%n", obstacle.getNameOfObstacle() ,obstacle.getBottomLeftX(), obstacle.getBottomLeftY(), obstacle.getTopRightX(), obstacle.getTopRightY(), obstacle.getSize());
        }
    }
}