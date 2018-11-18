package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;

public class UtilsTest {

    @Test
    public void testRequiredFieldSet() {
        HashSet<String> requiredFieldSet =
                Utils.requiredFieldSet("alpha", "bravo", "charlie");
        assert(requiredFieldSet.contains("alpha"));
        assert(requiredFieldSet.contains("bravo"));
        assert(requiredFieldSet.contains("charlie"));
    }

    @Test
    public void testContainsRequiredFields() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("age", 21);
        obj.put("name", "bob");
        obj.put("profession", "doctor");

        HashSet<String> required = Utils.requiredFieldSet("age", "name", "profession");

        assert(Utils.containsRequiredFields(obj, required));
    }
}
