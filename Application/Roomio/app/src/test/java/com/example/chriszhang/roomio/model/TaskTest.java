package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TaskTest {
    Task task = new Task(
            "123a",
            "bob",
            "joe",
            "trash",
            "take out the trash",
            "12/11");

    @Test
    public void testToJson() throws Exception {

        JSONObject obj = task.toJson();
        assert(obj.get("task_id").equals("123a"));
        assert(obj.get("assignee").equals("bob"));
        assert(obj.get("assigner").equals("joe"));
        assert(obj.get("task_name").equals("trash"));
        assert(obj.get("description").equals("take out the trash"));
        assert(obj.get("date_assigned").equals("12/11"));
    }

    @Test
    public void testToString() throws Exception {
        String output = task.toString();
        assert(output.contains("\"task_name\":\"trash\""));
        assert(output.contains("\"assignee\":\"bob\""));
        assert(output.contains("\"assigner\":\"joe\""));
        assert(output.contains("\"task_id\":\"123a\""));
        assert(output.contains("\"task_name\":\"trash\""));
        assert(output.contains("\"description\":\"take out the trash\""));
        assert(output.contains("\"date_assigned\":\"12/11\""));
    }
}
