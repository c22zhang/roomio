package com.example.chriszhang.roomio.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents an object that can be serialized to and from JSON.
 * Will be used for data model classes.
 */
public interface Jsonable {

    public JSONObject toJson() throws JSONException;
}
