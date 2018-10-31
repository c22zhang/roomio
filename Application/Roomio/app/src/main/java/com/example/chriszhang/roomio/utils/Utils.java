package com.example.chriszhang.roomio.utils;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.chriszhang.roomio.model.JsonToObjectException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Various Android related utils defined to avoid some repeated code
 */
public class Utils {

    /**
     * Adds required fields for JSON parsing to a Set
     * @param required 1...n fields that are required
     * @return a set containing all the required fields
     */
    public static HashSet<String> requiredFieldSet(String... required){
        HashSet<String> output = new HashSet<>();
        for(String s : required){
            output.add(s);
        }
        return output;
    }

    /**
     * Checks if a JSONobject contains the required fields
     * @param obj the JSONObject to check
     * @param requiredFields the required fields in a Set
     * @return true if the object contains all of the fields, false otherwise
     */
    public static boolean containsRequiredFields(JSONObject obj, Set<String> requiredFields){
        for(String field: requiredFields){
            if(!obj.has(field)){
                return false;
            }
        }
        return true;
    }
}
