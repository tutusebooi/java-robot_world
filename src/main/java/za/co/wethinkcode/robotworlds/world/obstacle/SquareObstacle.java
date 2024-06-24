package za.co.wethinkcode.robotworlds.world.obstacle;

public class SquareObstacle extends AbstractObstacle {

    public SquareObstacle(int xPosition, int yPosition, int sizeOfObstacle, boolean canSeePassIt, String nameOfObstacle) {
        super(xPosition, yPosition, sizeOfObstacle, canSeePassIt, nameOfObstacle);
    }

    public SquareObstacle(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    @Override
    public boolean isCanSeePassIt() {
        return false;
    }

    @Override
    public int thedefaultSizeOfObstacle() {
        return 4;
    }

    @Override
    public String theDefaultName() {
        return "SquareObstacle";
    }
}
