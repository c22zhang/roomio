package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Test;
import java.util.Optional;

public class UserTest {

    Optional<String> empty = Optional.empty();

    User user = new User(
            "asdf",
            "test",
            "Testy McTesterson",
            "test@gmail.com",
            Optional.of("1234"),
            empty);

    Notification notification = new Notification(
            "asdfasdf",
            "bob",
            "joe",
            Notification.Type.CLEAR_TAB_REQ);

    Tab tab = new Tab(
            "asdf",
            "your food",
            "bob",
            "joe",
            "12/12",
            123.12);

    @Test
    public void testGetters() {
        assert(user.getUserId().equals("asdf"));
        assert(user.getUsername().equals("test"));
        assert(user.getName().equals("Testy McTesterson"));
        assert(user.getEmail().equals("test@gmail.com"));
        assert(user.getAdminedGroupId().equals(Optional.of("1234")));
        assert(user.getHouseholdGroupId().equals(Optional.empty()));
    }

    @Test
    public void testSetters() {
        User copy = user;
        copy.setAdminedGroupId(Optional.of("1231"));
        assert(user.getAdminedGroupId().equals(Optional.of("1231")));
        copy.setHouseholdGroupId(Optional.of("0000"));
        assert(user.getHouseholdGroupId().equals(Optional.of("0000")));
    }

    @Test
    public void testAddTab() {
        user.addTab(tab);
        assert(user.getMyTabs().get(0).equals(tab));
    }

    @Test
    public void testAddNotification() {
        user.addNotification(notification);
        assert(user.getNotifications().get(0).equals(notification));
    }

    @Test
    public void testDeleteTab() {
        user.addTab(tab);
        user.deleteTab(tab);
        assert(user.getMyTabs().isEmpty());
    }

    @Test
    public void testDeleteNotification() {
        user.addNotification(notification);
        user.deleteNotification(notification);
        assert(user.getNotifications().isEmpty());
    }

    @Test
    public void testToJson() throws Exception {
        user.addTab(tab);
        user.addNotification(notification);
        JSONObject obj = user.toJson();
        assert(obj.get("user_id").equals("asdf"));
        assert(obj.get("username").equals("test"));
        assert(obj.get("name").equals("Testy McTesterson"));
        assert(obj.get("email").equals("test@gmail.com"));
        assert(obj.get("admined_group_id").equals("1234"));
        assert(!obj.has("member_group_id"));

        JSONObject notification =
                (JSONObject) obj.getJSONArray("notifications").opt(0);

        JSONObject tab =
                (JSONObject) obj.getJSONArray("involved_tabs").opt(0);

        assert(notification.get("notification_id").equals("asdfasdf"));
        assert(notification.get("to_user_id").equals("bob"));
        assert(notification.get("from_user_id").equals("joe"));
        assert(notification.get("notification_type").equals("CLEAR_TAB_REQ"));

        assert(tab.get("tab_id").equals("asdf"));
        assert(tab.get("reason").equals("your food"));
        assert(tab.get("assignee").equals("bob"));
        assert(tab.get("assigner").equals("joe"));
        assert(tab.get("date_assigned").equals("12/12"));
        assert(tab.get("amount").equals(123.12));
    }

    @Test
    public void testToString() throws Exception {
        user.addTab(tab);
        user.addNotification(notification);
        String output = user.toString();
        assert(output.contains("\"user_id\":\"asdf\""));
        assert(output.contains("\"username\":\"test\""));
        assert(output.contains("\"name\":\"Testy McTesterson\""));
        assert(output.contains("\"email\":\"test@gmail.com\""));
        assert(output.contains("\"admined_group_id\":\"1234\""));

        assert(output.contains("\"notification_id\":\"asdfasdf\""));
        assert(output.contains("\"to_user_id\":\"bob\""));
        assert(output.contains("\"from_user_id\":\"joe\""));
        assert(output.contains("\"notification_type\":\"CLEAR_TAB_REQ\""));

        assert(output.contains("\"tab_id\":\"asdf\""));
        assert(output.contains("\"reason\":\"your food\""));
        assert(output.contains("\"assignee\":\"bob\""));
        assert(output.contains("\"assigner\":\"joe\""));
        assert(output.contains("\"date_assigned\":\"12/12\""));
        assert(output.contains("\"amount\":123.12"));
    }

    @Test
    public void testFrom() throws Exception {
        user.addTab(tab);
        user.addNotification(notification);
        JSONObject obj = user.toJson();
        User user = User.from(obj);
        System.out.println(user.toJson().toString(4));
        assert(user.getUserId().equals("asdf"));
        assert(user.getUsername().equals("test"));
        assert(user.getName().equals("Testy McTesterson"));
        assert(user.getEmail().equals("test@gmail.com"));
        assert(user.getAdminedGroupId().equals(Optional.of("1234")));
        assert(user.getHouseholdGroupId().equals(Optional.empty()));

        assert(user.getMyTabs().get(0).getTabId().equals(tab.getTabId()));
        assert(user.getNotifications().get(0).getNotificationId()
                .equals(notification.getNotificationId()));
    }

}
