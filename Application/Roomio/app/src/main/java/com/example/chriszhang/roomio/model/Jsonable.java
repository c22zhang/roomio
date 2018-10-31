package com.example.chriszhang.roomio.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents an object that can be serialized to and from JSON.
 * Will be used for data model classes.
 */
public interface Jsonable {

    /**
     * @return A JSON object representation of whatever subclass is overriding this.
     * @throws JSONException if something goes wrong while parsing JSON
     */
    public JSONObject toJson() throws JSONException;
}
