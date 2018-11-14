package com.example.chriszhang.roomio.utils;

import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.chriszhang.roomio.model.JsonToObjectException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
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

    public static<T> boolean isPresent(Optional<T> optional, String errorMsg, View v){
        if(optional.isPresent()){
            return true;
        } else {
            Snackbar.make(v, errorMsg, Snackbar.LENGTH_LONG).show();
            return false;
        }
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}
