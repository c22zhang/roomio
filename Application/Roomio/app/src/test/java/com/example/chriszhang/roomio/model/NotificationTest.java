package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Test;

public class NotificationTest {

    Notification notification = new Notification(
            "asdfasdf",
            "bob",
            "joe",
            Notification.Type.CLEAR_TAB_REQ);

    @Test
    public void testGetters() {
        assert(notification.getNotificationId().equals("asdfasdf"));
        assert(notification.getToUserId().equals("bob"));
        assert(notification.getFromUserId().equals("joe"));
        assert(notification.getNotificationType().equals(Notification.Type.CLEAR_TAB_REQ));
        assert(notification.isClearable());
    }

    @Test
    public void testToJson() throws Exception {
        JSONObject obj = notification.toJson();
        assert(obj.get("notification_id").equals("asdfasdf"));
        assert(obj.get("to_user_id").equals("bob"));
        assert(obj.get("from_user_id").equals("joe"));
        assert(obj.get("notification_type").equals("CLEAR_TAB_REQ"));
    }

    @Test
    public void testToString() throws Exception {
        String output = notification.toString();
        assert(output.contains("\"notification_id\":\"asdfasdf\""));
        assert(output.contains("\"to_user_id\":\"bob\""));
        assert(output.contains("\"from_user_id\":\"joe\""));
        assert(output.contains("\"notification_type\":\"CLEAR_TAB_REQ\""));
    }
}
