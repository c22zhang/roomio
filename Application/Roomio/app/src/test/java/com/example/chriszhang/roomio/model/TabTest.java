package com.example.chriszhang.roomio.model;

import org.json.JSONObject;
import org.junit.Test;

public class TabTest {

    Tab tab = new Tab(
            "asdf",
            "food",
            "your food",
            "bob",
            "joe",
                    "12/12",
            123.12);

    @Test
    public void testGetters() {
        assert(tab.getTabId().equals("asdf"));
        assert(tab.getTabName().equals("food"));
        assert(tab.getReason().equals("your food"));
        assert(tab.getAssigneeUserId().equals("bob"));
        assert(tab.getAssignerUserId().equals("joe"));
        assert(tab.getDateAssigned().equals("12/12"));
        assert(tab.getAmount().equals(123.12));
    }

    @Test
    public void testSetters() {
        Tab copy = tab;
        copy.setTabName("rent");
        copy.setReason("pay ur rent dude");
        copy.setAmount(5000.00);
        copy.setAssigneeUserId("asdf");
        assert(copy.getTabName().equals("rent"));
        assert(copy.getReason().equals("pay ur rent dude"));
        assert(copy.getAmount().equals(5000.00));
        assert(copy.getAssigneeUserId().equals("asdf"));
    }

    @Test
    public void testToJson() throws Exception {
        JSONObject obj = tab.toJson();
        assert(obj.get("tab_id").equals("asdf"));
        assert(obj.get("tab_name").equals("food"));
        assert(obj.get("reason").equals("your food"));
        assert(obj.get("assignee").equals("bob"));
        assert(obj.get("assigner").equals("joe"));
        assert(obj.get("date_assigned").equals("12/12"));
        assert(obj.get("amount").equals(123.12));
    }

    @Test
    public void testToString() throws Exception {
        String output = tab.toString();
        assert(output.contains("\"tab_id\":\"asdf\""));
        assert(output.contains("\"tab_name\":\"food\""));
        assert(output.contains("\"reason\":\"your food\""));
        assert(output.contains("\"assignee\":\"bob\""));
        assert(output.contains("\"assigner\":\"joe\""));
        assert(output.contains("\"date_assigned\":\"12/12\""));
        assert(output.contains("\"amount\":123.12"));
    }
}
