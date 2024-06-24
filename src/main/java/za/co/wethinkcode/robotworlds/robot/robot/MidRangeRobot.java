package za.co.wethinkcode.robotworlds.robot.robot;

import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.world.World;

public class MidRangeRobot extends AbstractRobot {

    public MidRangeRobot(String name) {
        super(name);
    }

    public MidRangeRobot(String name, String typeOfRobot, int bulletShootCapacity, int distanceOfShoot) {
        super(name, typeOfRobot, bulletShootCapacity, distanceOfShoot);
    }

    @Override
    public String theTypeOfRobotSet() {
        return "MidRange Shooter Robot";
    }

    @Override
    public int theDefaultNumberOfAmmo() {
        return 3;
    }

    @Override
    public int theDefaultDistance() {
        return 10;
    }

    @Override
    public boolean executeShootCommand(World world) {
        // Checks if robot bullet capacity is not zero.
        if (this.getRobotBullet().getBulletShootCapacity() == 0) {
            this.setStatus("Robot needs to reload!!");
            return false;
        }
        // Removes a bullet from robot that shoot
        this.getRobotBullet().removeBulletCapacity();
        String status = "You took a shot and missed!";
        // Removes shield of robot that it hit and set status of the robot who shot

        int distanceOfShoot = getRobotBullet().getDistanceOfShoot();

        if (Direction.NORTH.equals(this.getCurrentDirection())) {
            int x = this.getPosition().getX();

            for (int y = this.getPosition().getY()+1; y <= this.getPosition().getY()+distanceOfShoot; y++) {
                this.getRobotBullet().setPositionOfBullet(new Position(x, y));
                for (AbstractRobot robotEnemy: world.getRobots()) {
                    if (!this.getName().equals(robotEnemy.getName()) && this.getRobotBullet().didBulletHitRobot(robotEnemy)) {
                        robotEnemy.getRobotShield().removeShieldAfterHit();
                        robotEnemy.getRobotShield().checkIfShieldOn();
                        robotEnemy.setStatus("You have been shot by " + this.getName() + " at SOUTH of you!!");
                        status = "You shot " + robotEnemy.getName() + " at position: " + robotEnemy.getPosition().toString() + " at direction: " + robotEnemy.getCurrentDirection().toString();
                    }

                }
            }
        } else if (Direction.SOUTH.equals(this.getCurrentDirection())) {
            int x = this.getPosition().getX();

            for (int y = this.getPosition().getY()-1; y >= this.getPosition().getY()-distanceOfShoot; y--) {
                this.getRobotBullet().setPositionOfBullet(new Position(x, y));
                for (AbstractRobot robotEnemy: world.getRobots()) {
                    if (!this.getName().equals(robotEnemy.getName()) && this.getRobotBullet().didBulletHitRobot(robotEnemy)) {
                        robotEnemy.getRobotShield().removeShieldAfterHit();
                        robotEnemy.getRobotShield().checkIfShieldOn();
                        robotEnemy.setStatus("You have been shot by " + this.getName() + " at NORTH of you!!");
                        status = "You shot " + robotEnemy.getName() + " at position: " + robotEnemy.getPosition().toString() + " at direction: " + robotEnemy.getCurrentDirection().toString();
                    }

                }
            }
        } else if (Direction.EAST.equals(this.getCurrentDirection())) {
            int y = this.getPosition().getY();

            for (int x = this.getPosition().getX()+1; x <= this.getPosition().getX()+distanceOfShoot; x++) {
                this.getRobotBullet().setPositionOfBullet(new Position(x, y));
                for (AbstractRobot robotEnemy: world.getRobots()) {
                    if (!this.getName().equals(robotEnemy.getName()) && this.getRobotBullet().didBulletHitRobot(robotEnemy)) {
                        robotEnemy.getRobotShield().removeShieldAfterHit();
                        robotEnemy.getRobotShield().checkIfShieldOn();
                        robotEnemy.setStatus("You have been shot by " + this.getName() + " at WEST of you!!");
                        status = "You shot " + robotEnemy.getName() + " at position: " + robotEnemy.getPosition().toString() + " at direction: " + robotEnemy.getCurrentDirection().toString();
                    }

                }
            }
        } else if (Direction.WEST.equals(this.getCurrentDirection())) {
            int y = this.getPosition().getY();

            for (int x = this.getPosition().getX()-1; x >= this.getPosition().getX()-distanceOfShoot; x--) {
                this.getRobotBullet().setPositionOfBullet(new Position(x, y));
                for (AbstractRobot robotEnemy: world.getRobots()) {
                    if (!this.getName().equals(robotEnemy.getName()) && this.getRobotBullet().didBulletHitRobot(robotEnemy)) {
                        robotEnemy.getRobotShield().removeShieldAfterHit();
                        robotEnemy.getRobotShield().checkIfShieldOn();
                        robotEnemy.setStatus("You have been shot by " + this.getName() + " at EAST of you!!");
                        status = "You shot " + robotEnemy.getName() + " at position: " + robotEnemy.getPosition().toString() + " at direction: " + robotEnemy.getCurrentDirection().toString();
                    }

                }
            }
        }
        // Set the bullets position to null, so it doesn't interrupt or within the game.
        this.getRobotBullet().setPositionOfBullet(null);
        if ("You took a shot and missed!".equals(status)) {
            this.setStatus(status);
            return false;
        }
        this.setStatus(status);
        return true;
    }
}
