package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Optional;

public class NotificationTest {

    Task clearable = new Task("asdf",
            "joe",
            "bob",
            "asdf",
            "asdfasdfasdf",
            "12/11");

    Notification notification = new Notification(
            "asdfasdf",
            "insert message here",
            "bob",
            "joe",
            Notification.Type.CLEAR_TAB_REQ,
            Optional.<Jsonable>empty());

    Notification withTask = new Notification(
            "1111",
            "message",
            "bob",
            "joe",
            Notification.Type.CLEAR_TASK_REQ,
            Optional.<Jsonable>of(clearable));

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
        assert(obj.get("message").equals("insert message here"));
        assert(obj.get("to_user_id").equals("bob"));
        assert(obj.get("from_user_id").equals("joe"));
        assert(obj.get("notification_type").equals("CLEAR_TAB_REQ"));

        JSONObject obj2 = withTask.toJson();
        System.out.println(obj2.get("potential_clear"));
        System.out.println(clearable.toJson());
        assert(obj2.get("potential_clear").toString().equals(clearable.toJson().toString()));
    }

    @Test
    public void testToString() {
        String output = notification.toString();
        assert(output.contains("\"notification_id\":\"asdfasdf\""));
        assert(output.contains("\"message\":\"insert message here\""));
        assert(output.contains("\"to_user_id\":\"bob\""));
        assert(output.contains("\"from_user_id\":\"joe\""));
        assert(output.contains("\"notification_type\":\"CLEAR_TAB_REQ\""));
    }

    @Test
    public void testFrom() throws Exception {
        JSONObject obj = notification.toJson();
        Notification notification = Notification.from(obj);
        assert(notification.getNotificationId().equals("asdfasdf"));
        assert(notification.getToUserId().equals("bob"));
        assert(notification.getFromUserId().equals("joe"));
        assert(notification.getNotificationType().equals(Notification.Type.CLEAR_TAB_REQ));
        assert(notification.isClearable());

        JSONObject obj2 = withTask.toJson();
        Notification not2 = Notification.from(obj2);
        assert(not2.getPotentialClear().isPresent());
        assert(not2.getPotentialClear().get().equals(clearable));
    }
}
