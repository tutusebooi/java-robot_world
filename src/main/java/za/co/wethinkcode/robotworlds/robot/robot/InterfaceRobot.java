package za.co.wethinkcode.robotworlds.robot.robot;

import za.co.wethinkcode.robotworlds.robot.Direction;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

public interface InterfaceRobot {

    public String getName();

    public Position getPosition();

    public Direction getCurrentDirection();

    public  String  getStatus();

    public void setCurrentDirection(Direction currentDirection);

    public  void  setStatus(String status);

    public void setPosition(Position newPosition);

    public void setName(String name);

    public ArrayList<String> executeLookCommand(World world);

    public String toString();
}
