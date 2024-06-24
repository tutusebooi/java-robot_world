package za.co.wethinkcode.robotworlds.robot.robot;

import za.co.wethinkcode.robotworlds.robot.*;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.obstacle.AbstractObstacle;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRobot implements InterfaceRobot {
    private String name;
    private String typeOfRobot;
    private Position position;
    private Direction currentDirection;
    private String status;
    private Bullet robotBullet;
    private Shield robotShield;

    protected AbstractRobot(String name) {
        this.name = name;
        this.status = "Ready";
        this.currentDirection = Direction.NORTH;
        this.robotShield = new Shield();
        this.robotBullet = new Bullet(theDefaultNumberOfAmmo(), theDefaultDistance());
        this.typeOfRobot = theTypeOfRobotSet();
    }

    protected AbstractRobot(String name, String typeOfRobot, int bulletShootCapacity, int distanceOfShoot) {
        this.name = name;
        this.status = "Ready";
        this.currentDirection = Direction.NORTH;
        this.robotShield = new Shield();
        this.robotBullet = new Bullet(bulletShootCapacity, distanceOfShoot);
        this.typeOfRobot = typeOfRobot;
    }

    // get the robot's name
    public String getName(){
        return this.name;
    }

    // get the type of robot
    public String getTypeOfRobot() {
        return this.typeOfRobot;
    }

    // get the robot position object
    public  Position getPosition(){
        return  this.position;
    }

    // get the current direction object
    public  Direction getCurrentDirection(){
        return this.currentDirection;
    }

    // get the robot status
    public  String  getStatus(){
        return this.status;
    }

    // get the robot bullet object
    public Bullet getRobotBullet() {
        return this.robotBullet;
    }

    // get the robot shield object
    public Shield getRobotShield() {
        return this.robotShield;
    }

    // set the current direction
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    // set the status of the robot
    public  void  setStatus(String status){
        this.status = status;
    }

    // set the robot's bullet/ammo
    public void setRobotBullet(Bullet robotBullet) {
        this.robotBullet = robotBullet;
    }

    // set the robot's shield
    public void setRobotShield(Shield robotShield) {
        this.robotShield = robotShield;
    }

    // set the position of the robot
    public void setPosition(Position newPosition){
        this.position = newPosition;
    }

    // Set name of robot
    public void setName(String name) {
        this.name = name;
    }

    // restore bullets/ammo
    public void restoreBullet() {
        this.robotBullet.setBulletShootCapacity(3);
    }

    // restore shield
    public void restoreShield() {
        this.robotShield.setNrOfShield(3);
    }

    // Default name of type of robot

    public abstract String theTypeOfRobotSet();

    public abstract int theDefaultNumberOfAmmo();

    public abstract int theDefaultDistance();

    // Look Command

    public ArrayList<String> executeLookCommand(World world) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        this.setStatus("Took a look and saw nothing in my line of vision.");

        List<Position> xPostions = new ArrayList<>(List.of());
        List<Position> yPostions = new ArrayList<>(List.of());
        ArrayList<String> status = new ArrayList<>(List.of());

        for (int i = world.getTopLeft().getX(); i <= world.getBottomRight().getX(); i++) {
            if (!this.getPosition().equals(new Position(i, y))) {
                xPostions.add(new Position(i, y));
            }
        }
        for (int t = world.getBottomRight().getY(); t <= world.getTopLeft().getY(); t++) {
            if (!this.getPosition().equals(new Position(x, t))) {
                yPostions.add(new Position(x, t));
            }
        }

        boolean canNotSeePass = false;

        String direction = Direction.SOUTH.toString();

        for (Position yPosition : yPostions) {

            if (yPosition.getY() > y) {
                direction = Direction.NORTH.toString();
            }

            if (!canNotSeePass) {
                for (AbstractRobot robotInWorld : world.getRobots()) {
                    if (robotInWorld.getPosition().equals(yPosition)) {
                        status.add("Robot " + robotInWorld.getName() + " is at the " + direction + ", at position: " + robotInWorld.getPosition());
                    }

                }
                for (AbstractObstacle obstacle : world.getObstacles()) {
                    if (obstacle.blocksPosition(yPosition)) {
                        status.add("Obstacle at the " + direction + ". Obstacle is: " + obstacle);
                        if (!obstacle.isCanSeePassIt()) {
                            canNotSeePass = true;
                        }
                    }
                }
            }
        }

        canNotSeePass = false;

        direction = Direction.WEST.toString();

        for (Position xPosition : xPostions) {

            if (xPosition.getX() > x) {
                direction = Direction.EAST.toString();
            }

            if (!canNotSeePass) {
                for (AbstractRobot robotInWorld : world.getRobots()) {
                    if (robotInWorld.getPosition().equals(xPosition)) {
                        status.add("Robot " + robotInWorld.getName() + " is at the " + direction + ", at position: " + robotInWorld.getPosition());
                    }

                }
                for (AbstractObstacle obstacle : world.getObstacles()) {
                    if (obstacle.blocksPosition(xPosition)) {
                        status.add("Obstacle at the " + direction + ". Obstacle is: " + obstacle);
                        if (!obstacle.isCanSeePassIt()) {
                            canNotSeePass = true;
                        }
                    }
                }
            }
        }

        if (!status.isEmpty()) {
            ArrayList<String> newStatus = removeDuplicatesInArray(status);
            if (newStatus.size() > 1) {
                this.setStatus(newStatus.toString());
            } else {
                this.setStatus(newStatus.get(0));
            }
            return newStatus;
        }

        return null;
    }

    public static ArrayList<String> removeDuplicatesInArray(ArrayList<String> list) {

        ArrayList<String> newList = new ArrayList<>();

        for (String item: list) {
            if (!newList.contains(item)) {
                newList.add(item);
            }
        }

        return newList;
    }

    // Shoot Command

    public abstract boolean executeShootCommand(World world);

    // The toString
    @Override
    public String toString(){
        return  "[" + this.position.getX() + "," + this.position.getY() + "] Current Direction(" + this.currentDirection.toString() + ") " + this.name + " > " + this.status;}
}
