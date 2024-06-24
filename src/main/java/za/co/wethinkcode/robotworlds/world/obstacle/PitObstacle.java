package za.co.wethinkcode.robotworlds.world.obstacle;

public class PitObstacle extends AbstractObstacle {

    public PitObstacle(int xPosition, int yPosition, int sizeOfObstacle, boolean canSeePassIt, String nameOfObstacle) {
        super(xPosition, yPosition, sizeOfObstacle, canSeePassIt, nameOfObstacle);
    }

    public PitObstacle(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    @Override
    public boolean isCanSeePassIt() {
        return true;
    }

    @Override
    public int thedefaultSizeOfObstacle() {
        return 6;
    }

    @Override
    public String theDefaultName() {
        return "Pit";
    }
}
