package za.co.wethinkcode.robotworlds.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;

public class GsonSerialization {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(AbstractRobot.class, new AbstractRobotDeserializer())
            .create();

    public static String fromGsonRequestTOJson(GsonRequest gsonRequest) {
        return gson.toJson(gsonRequest);
    }

    public static String fromGsonResponseTOJson(GsonResponse gsonResponse) {
        return gson.toJson(gsonResponse);
    }

    public static GsonRequest fromJsonToGsonRequest(String jsonString) {
        return gson.fromJson(jsonString, GsonRequest.class);
    }

    public static GsonResponse fromJsonToGsonResponse(String jsonString) {
        return gson.fromJson(jsonString, GsonResponse.class);
    }
}
