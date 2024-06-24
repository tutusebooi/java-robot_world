package za.co.wethinkcodetest.gson;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.gson.GsonRequest;
import za.co.wethinkcode.robotworlds.gson.GsonResponse;
import za.co.wethinkcode.robotworlds.gson.GsonSerialization;
import za.co.wethinkcode.robotworlds.robot.robot.AbstractRobot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GsonSerializationTest {

    @Test
    public void testFromGsonRequestTOJson() {
        GsonRequest gsonRequest = new GsonRequest(new MyRobot("Kyle"), "command", new ArrayList<>());
        String json = GsonSerialization.fromGsonRequestTOJson(gsonRequest);
        assertNotNull(json);
    }

    @Test
    public void testFromGsonResponseTOJson() {
        GsonResponse gsonResponse = new GsonResponse("result", new HashMap<>(), new HashMap<>(), true, new MyRobot("Kyle"));
        String json = GsonSerialization.fromGsonResponseTOJson(gsonResponse);
        assertNotNull(json);
    }

//    @Test
//    public void testFromJsonToGsonRequest() {
//        String jsonString = "{\"robot\":{\"name\":\"TestRobot\",\"position\":null,\"direction\":null},\"command\":\"command\",\"argument\":[]}";
//        GsonRequest gsonRequest = GsonSerialization.fromJsonToGsonRequest(jsonString);
//        assertNotNull(gsonRequest);
//        assertEquals("command", gsonRequest.getGsonCommand());
//    }
//
//    @Test
//    public void testFromJsonToGsonResponse() {
//        String jsonString = "{\"result\":\"result\",\"data\":{},\"state\":{},\"stillInPlay\":true,\"robot\":{\"name\":\"TestRobot\",\"position\":null,\"direction\":null}}";
//        GsonResponse gsonResponse = GsonSerialization.fromJsonToGsonResponse(jsonString);
//        assertNotNull(gsonResponse);
//        assertEquals("result", gsonResponse.getResult());
//    }

    private static class MyRobot extends AbstractRobot {
        protected MyRobot(String name) {
            super(name);
        }

        @Override
        public String theTypeOfRobotSet() {
            return null;
        }

        @Override
        public int theDefaultNumberOfAmmo() {
            return 0;
        }

        @Override
        public int theDefaultDistance() {
            return 0;
        }

        @Override
        public boolean executeShootCommand(World world) {
            return false;
        }
    }
}
