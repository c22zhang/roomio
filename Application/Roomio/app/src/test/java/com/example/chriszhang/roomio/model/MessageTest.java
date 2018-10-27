package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Test;

public class MessageTest {

    Message message = new Message(
            "addddsa",
            "Hello world",
            "bob",
                    "12/1");

    @Test
    public void testToJson() throws Exception {
        JSONObject obj = message.toJson();
        assert(obj.get("message_id").equals("addddsa"));
        assert(obj.get("message_contents").equals("Hello world"));
        assert(obj.get("sender_id").equals("bob"));
        assert(obj.get("date_sent").equals("12/1"));
    }

    @Test
    public void testToString() throws Exception {
        String output = message.toString();
        assert(output.contains("\"message_id\":\"addddsa\""));
        assert(output.contains("\"message_contents\":\"Hello world\""));
        assert(output.contains("\"sender_id\":\"bob\""));
        assert(output.contains("\"date_sent\":\"12/1\""));
    }
}
