package za.co.wethinkcode.robotworlds.world.obstacle;

public class MountainObstacle extends AbstractObstacle {

    public MountainObstacle(int xPosition, int yPosition, int sizeOfObstacle, boolean canSeePassIt, String nameOfObstacle) {
        super(xPosition, yPosition, sizeOfObstacle, canSeePassIt, nameOfObstacle);
    }

    public MountainObstacle(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    @Override
    public boolean isCanSeePassIt() {
        return false;
    }

    @Override
    public int thedefaultSizeOfObstacle() {
        return 10;
    }

    @Override
    public String theDefaultName() {
        return "Mountain";
    }
}
