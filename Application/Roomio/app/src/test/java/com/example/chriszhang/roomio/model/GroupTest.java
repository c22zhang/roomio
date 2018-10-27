package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Optional;

public class GroupTest {

    Group group = new Group(
            "asdf",
            "bob",
            "123 Mulberry ln");

    User user = new User(
            "asdf",
                    "test",
                    "Testy McTesterson",
                    "test@gmail.com",
                    "password",
            Optional.of("1234"),
            Optional.of("2345"));

    Message message = new Message(
            "addddsa",
            "Hello world",
            "bob",
            "12/1");

    Task task = new Task(
            "123a",
            "bob",
            "joe",
            "trash",
            "take out the trash",
            "12/11");

    @Test
    public void testAddMember() {
        group.addMember(user);
        assert(group.getMembers().get(0).equals(user));
    }

    @Test
    public void testAddTask() {
        group.addTask(task);
        assert(group.getTasks().get(0).equals(task));
    }

    @Test
    public void testAddMessage() {
        group.addMessage(message);
        assert(group.getGroupChatMessages().get(0).equals(message));
    }

    @Test
    public void testRemoveMember() {
        group.addMember(user);
        group.removeMember(user);
        assert(group.getMembers().isEmpty());
    }

    @Test
    public void testDeleteTask() {
        group.addTask(task);
        group.deleteTask(task);
        assert(group.getTasks().isEmpty());
    }

    @Test
    public void testMaybeGetMember() {
        assert(group.maybeGetMember(user).equals(Optional.empty()));
        group.addMember(user);
        assert(group.maybeGetMember(user).equals(Optional.of(user)));
    }

    @Test
    public void testMaybeGetTask() {
        assert(group.maybeGetTask(task).equals(Optional.empty()));
        group.addTask(task);
        assert(group.maybeGetTask(task).equals(Optional.of(task)));
    }

    @Test
    public void testToJson() throws Exception {
        group.addMember(user);
        group.addMessage(message);
        group.addTask(task);
        JSONObject obj = group.toJson();
        assert(obj.get("group_id").equals("asdf"));
        assert(obj.get("group_name").equals("123 Mulberry ln"));
        assert(obj.get("group_admin_user_id").equals("bob"));

        JSONObject user =
                (JSONObject) obj.getJSONArray("members").opt(0);
        JSONObject message =
                (JSONObject) obj.getJSONArray("messages").opt(0);
        JSONObject task =
                (JSONObject) obj.getJSONArray("group_tasks").opt(0);

        assert(user.get("user_id").equals("asdf"));
        assert(user.get("username").equals("test"));
        assert(user.get("name").equals("Testy McTesterson"));
        assert(user.get("email").equals("test@gmail.com"));
        assert(user.get("admined_group_id").equals("1234"));
        assert(user.get("member_group_id").equals("2345"));

        assert(message.get("message_id").equals("addddsa"));
        assert(message.get("message_contents").equals("Hello world"));
        assert(message.get("sender_id").equals("bob"));
        assert(message.get("date_sent").equals("12/1"));

        assert(task.get("task_id").equals("123a"));
        assert(task.get("assignee").equals("bob"));
        assert(task.get("assigner").equals("joe"));
        assert(task.get("task_name").equals("trash"));
        assert(task.get("description").equals("take out the trash"));
        assert(task.get("date_assigned").equals("12/11"));
    }

    @Test
    public void testToString() {
        group.addMember(user);
        group.addMessage(message);
        group.addTask(task);
        String output = group.toString();

        assert(output.contains("\"group_id\":\"asdf\""));
        assert(output.contains("\"group_name\":\"123 Mulberry ln\""));
        assert(output.contains("\"group_admin_user_id\":\"bob\""));

        assert(output.contains("\"user_id\":\"asdf\""));
        assert(output.contains("\"username\":\"test\""));
        assert(output.contains("\"name\":\"Testy McTesterson\""));
        assert(output.contains("\"email\":\"test@gmail.com\""));
        assert(output.contains("\"admined_group_id\":\"1234\""));
        assert(output.contains("\"member_group_id\":\"2345\""));

        assert(output.contains("\"task_name\":\"trash\""));
        assert(output.contains("\"assignee\":\"bob\""));
        assert(output.contains("\"assigner\":\"joe\""));
        assert(output.contains("\"task_id\":\"123a\""));
        assert(output.contains("\"task_name\":\"trash\""));
        assert(output.contains("\"description\":\"take out the trash\""));
        assert(output.contains("\"date_assigned\":\"12/11\""));

        assert(output.contains("\"message_id\":\"addddsa\""));
        assert(output.contains("\"message_contents\":\"Hello world\""));
        assert(output.contains("\"sender_id\":\"bob\""));
        assert(output.contains("\"date_sent\":\"12/1\""));
    }


}
