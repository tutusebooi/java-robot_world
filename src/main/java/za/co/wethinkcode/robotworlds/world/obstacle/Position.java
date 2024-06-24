package za.co.wethinkcode.robotworlds.world.obstacle;

public class Position {
    // Fields representing the x and y coordinates
    private int x;
    private int y;

    public Position(int x, int y) {

         // Constructor to initialize the Position with specific x and y coordinates
        this.x = x;
        this.y = y;
    }

     // Getter method for the x and ycoordinate
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

     // Overriding the toString method to provide a custom string representation of the Position object
    @Override
    public String toString() {
        return "Position[" + x + ", " + y + "]";
    }
}