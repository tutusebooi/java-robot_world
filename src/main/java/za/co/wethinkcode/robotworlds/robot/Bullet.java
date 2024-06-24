package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;

/**
 * This is the robots ammo/bullets.
 */
public class Bullet {
    private Position positionOfBullet;
    private Boolean didItHitARobot;
    private int bulletShootCapacity;
    private int distanceOfShoot;
    
    public Bullet(int bulletShootCapacity, int distanceOfShoot) {
        this.didItHitARobot = false;
        this.bulletShootCapacity = bulletShootCapacity;
        this.distanceOfShoot = distanceOfShoot;
    }

    public void setDidItHitARobot(Boolean didItHitARobot) {
        this.didItHitARobot = didItHitARobot;
    }

    public void setPositionOfBullet(Position positionOfBullet) {
        this.positionOfBullet = positionOfBullet;
    }

    public void setBulletShootCapacity(int bulletShootCapacity) {
        this.bulletShootCapacity = bulletShootCapacity;
    }

    public void removeBulletCapacity() {
        bulletShootCapacity -= 1;
    }

    public Position getPositionOfBullet() {
        return positionOfBullet;
    }

    public Boolean getDidItHitARobot() {
        return didItHitARobot;
    }

    public int getDistanceOfShoot() {
        return distanceOfShoot;
    }

    public int getBulletShootCapacity() {
        return bulletShootCapacity;
    }

    public boolean didBulletHitRobot(AbstractRobot robot) {
        return positionOfBullet.equals(robot.getPosition());
    }

    @Override
    public String toString() {
        return "Bullet { " +
                "positionOfBullet = " + positionOfBullet +
                ", didItHitARobot = " + didItHitARobot +
                ", bulletShootCapacity = " + bulletShootCapacity +
                ", distanceOfShoot = " + distanceOfShoot +
                '}';
    }
}
