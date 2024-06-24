package za.co.wethinkcode.robotworlds.world.obstacle;

import za.co.wethinkcode.robotworlds.robot.Position;

public interface InterfaceObstacle {

    public int getBottomLeftX();

    public int getBottomLeftY();

    public int getTopRightX();

    public int getTopRightY();

    public int getSize();

    public boolean canRobotSeePassObstacle();

    public boolean blocksPath(Position a, Position b);

    public boolean blocksPosition(Position position);
}
