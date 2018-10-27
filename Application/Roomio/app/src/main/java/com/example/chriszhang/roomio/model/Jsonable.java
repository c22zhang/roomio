package com.example.chriszhang.roomio.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface Jsonable {

    public JSONObject toJson() throws JSONException;
}
