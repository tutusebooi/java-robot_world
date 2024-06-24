package za.co.wethinkcode.robotworlds.serverandclient.client;

/**
 * This class represents the information of a robot.
 */
public class RobotInfo {
    private String type;
    private String name;

    /**
     * Constructs a new RobotInfo instance with the specified type and name.
     *
     * @param type The type of the robot.
     * @param name The name of the robot.
     */
    public RobotInfo(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Gets the type of the robot.
     *
     * @return The type of the robot.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the name of the robot.
     *
     * @return The name of the robot.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the robot information.
     *
     * @return A string containing the name and type of the robot.
     */
    @Override
    public String toString() {
        return "Name = " + name + ", Type = " + type;
    }
}
