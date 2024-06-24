package za.co.wethinkcode.robotworlds.world.obstacle;

public class LakeObstacle extends AbstractObstacle {

    public LakeObstacle(int xPosition, int yPosition, int sizeOfObstacle, boolean canSeePassIt, String nameOfObstacle) {
        super(xPosition, yPosition, sizeOfObstacle, canSeePassIt, nameOfObstacle);
    }

    public LakeObstacle(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }

    @Override
    public boolean isCanSeePassIt() {
        return false;
    }

    @Override
    public int thedefaultSizeOfObstacle() {
        return 15;
    }

    @Override
    public String theDefaultName() {
        return "Lake";
    }
}
