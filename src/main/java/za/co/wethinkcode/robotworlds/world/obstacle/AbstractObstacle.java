package za.co.wethinkcode.robotworlds.world.obstacle;

import za.co.wethinkcode.robotworlds.robot.Position;

public abstract class AbstractObstacle implements InterfaceObstacle {
    private int BottomLeftX;
    private int BottomLeftY ;
    private int TopRightX ;
    private int TopRightY;
    private int sizeOfObstacle;
    private boolean canSeePassIt;
    private String nameOfObstacle;

    protected AbstractObstacle(int xPosition, int yPosition, int sizeOfObstacle, boolean canSeePassIt, String nameOfObstacle) {
        this.BottomLeftX = xPosition;
        this.BottomLeftY = yPosition;
        this.sizeOfObstacle = sizeOfObstacle;
        this.canSeePassIt = canSeePassIt;
        this.TopRightX = xPosition + sizeOfObstacle;
        this.TopRightY = yPosition + sizeOfObstacle;
        this.nameOfObstacle = nameOfObstacle;
    }

    protected AbstractObstacle(int xPosition, int yPosition) {
        this.BottomLeftX = xPosition;
        this.BottomLeftY = yPosition;
        this.sizeOfObstacle = thedefaultSizeOfObstacle();
        this.canSeePassIt = isCanSeePassIt();
        this.TopRightX = xPosition + sizeOfObstacle;
        this.TopRightY = yPosition + sizeOfObstacle;
        this.nameOfObstacle = theDefaultName();
    }

    public int getBottomLeftX() {
        return this.BottomLeftX;
    }

    public int getBottomLeftY() {
        return this.BottomLeftY;
    }

    public int getTopRightX() {
        return this.TopRightX;
    }

    public int getTopRightY() {
        return this.TopRightY;
    }

    public int getSize(){
        return this.sizeOfObstacle;
    }

    public int getSizeOfObstacle() {
        return sizeOfObstacle;
    }

    public String getNameOfObstacle() {
        return nameOfObstacle;
    }

    public boolean canRobotSeePassObstacle() {
        return this.canSeePassIt;
    }

    public abstract boolean isCanSeePassIt();

    public abstract int thedefaultSizeOfObstacle();

    public abstract String theDefaultName();

    public boolean blocksPath(Position a, Position b) {
        if (((this.getBottomLeftX() <= a.getX()) && (a.getX() <= this.getTopRightX())) && ((this.getBottomLeftX() <= b.getX()) && (b.getX() <= this.getTopRightX()))) {
            return (this.getBottomLeftY() >= a.getY() && a.getY() <= this.getTopRightY()) && (this.getBottomLeftY() <= b.getY() && b.getY() >= this.getTopRightY()) ||
                    (this.getBottomLeftY() <= a.getY() && a.getY() >= this.getTopRightY()) && (this.getBottomLeftY() >= b.getY() && b.getY() <= this.getTopRightY());
        } else if (((this.getBottomLeftY() <= a.getY()) && (a.getY() <= this.getTopRightY())) && ((this.getBottomLeftY() <= b.getY()) && (b.getY() <= this.getTopRightY()))) {
            return (this.getBottomLeftX() >= a.getX() && a.getX() <= this.getTopRightX()) && (this.getBottomLeftX() <= b.getX() && b.getX() >= this.getTopRightX()) ||
                    (this.getBottomLeftX() <= a.getX() && a.getX() >= this.getTopRightX()) && (this.getBottomLeftX() >= b.getX() && b.getX() <= this.getTopRightX());
        }
        return false;
    }

    public boolean blocksPosition(Position position) {
        position.isIn(new Position(getBottomLeftX(), getBottomLeftY()), new Position(getTopRightX(), getTopRightY()));

        return (this.getBottomLeftY() <= position.getY() && position.getY() <= this.getTopRightY()) &&
                (this.getBottomLeftX() <= position.getX() && position.getX() <= this.getTopRightX());
    }

    @Override
    public String toString() {
        return getNameOfObstacle() + " Position: BottomLeft[" + BottomLeftX + ", " + BottomLeftY  + "]" + "; TopRight[" + TopRightX + ", " + TopRightY + "] Size: " + getSize();
    }
}
