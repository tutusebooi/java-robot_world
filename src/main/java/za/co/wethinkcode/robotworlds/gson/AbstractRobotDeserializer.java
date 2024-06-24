package za.co.wethinkcode.robotworlds.gson;

import com.google.gson.*;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.robot.robot.Robot;
import za.co.wethinkcode.robotworlds.robot.Position;
import za.co.wethinkcode.robotworlds.robot.Bullet;
import za.co.wethinkcode.robotworlds.robot.Shield;
import za.co.wethinkcode.robotworlds.robot.Direction; // Ensure correct import for Direction enum

import java.lang.reflect.Type;

public class AbstractRobotDeserializer implements JsonDeserializer<AbstractRobot> {

    @Override
    public AbstractRobot deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract properties from the JSON object
        String name = jsonObject.get("name").getAsString();
        String typeOfRobot = jsonObject.get("typeOfRobot").getAsString();
        int bulletShootCapacity = jsonObject.has("bulletShootCapacity") ? jsonObject.get("bulletShootCapacity").getAsInt() : 0;
        int distanceOfShoot = jsonObject.has("distanceOfShoot") ? jsonObject.get("distanceOfShoot").getAsInt() : 0;

        // Extract position from the JSON object
        JsonObject positionObject = jsonObject.getAsJsonObject("position");
        int x = positionObject.has("x") ? positionObject.get("x").getAsInt() : 0;
        int y = positionObject.has("y") ? positionObject.get("y").getAsInt() : 0;
        Position position = new Position(x, y);

        // Extract bullet from the JSON object
        JsonObject bulletObject = jsonObject.getAsJsonObject("robotBullet");
        int bulletCapacity = bulletObject.has("bulletShootCapacity") ? bulletObject.get("bulletShootCapacity").getAsInt() : 0;
        int bulletDistance = bulletObject.has("distanceOfShoot") ? bulletObject.get("distanceOfShoot").getAsInt() : 0;
        Bullet bullet = new Bullet(bulletCapacity, bulletDistance);

        // Extract shield from the JSON object
        JsonObject shieldObject = jsonObject.getAsJsonObject("robotShield");
        int shieldCount = shieldObject.has("nrOfShield") ? shieldObject.get("nrOfShield").getAsInt() : 0;
        boolean shieldOn = shieldObject.has("shieldOn") ? shieldObject.get("shieldOn").getAsBoolean() : false;
        Shield shield = new Shield();
        shield.setNrOfShield(shieldCount);
        shield.checkIfShieldOn();

        // Extract currentDirection from the JSON object
        Direction currentDirection = Direction.valueOf(jsonObject.get("currentDirection").getAsString());

        // Create and return a new Robot instance with the extracted properties
        Robot robot = new Robot(name, typeOfRobot, bulletShootCapacity, distanceOfShoot);
        robot.setPosition(position);
        robot.setRobotBullet(bullet);
        robot.setRobotShield(shield);
        robot.setCurrentDirection(currentDirection); // Set the current direction
        return robot;
    }
}
