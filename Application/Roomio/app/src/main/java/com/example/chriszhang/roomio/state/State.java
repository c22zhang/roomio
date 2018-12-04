package com.example.chriszhang.roomio.state;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.User;

import org.json.JSONException;

import java.util.HashMap;

/**
 * A client-side wrapper class for the global User/Group data for the representing
 * the current state of the application for the user in the current session.
 */
public class State {

    private Group group;
    private User currentUser;

    private static State stateInstance = null;

    //For later use in avoiding network calls once backend is hooked up
    private static boolean testMode = false;

    private State(User currentUser){
        this.currentUser = currentUser;
    }

    public void logout(){
        stateInstance = null;
        group = null;
        currentUser = null;
    }

    public static boolean hasGroup() {
       return (stateInstance != null && stateInstance.group != null);
    }

    public static void setTestMode (boolean newMode){
        testMode = newMode;
    }

    public static boolean isInTestMode() { return testMode; }

    public static Group getGroup() {
        if(getStateInstance() != null){
            return getStateInstance().group;
        } else {
            return null;
        }
    }

    public static User getCurrentUser() {
        if(getStateInstance().currentUser != null){
            return getStateInstance().currentUser;

        } else {
            return null;
        }
    }

    public static State getStateInstance() {
        return stateInstance;
    }

    public static void setGroup (Group g){
        stateInstance.group = g;
    }

    public static void markGroupAsUpdated(Context ctx, View errView) {
        Client client = new Client(ctx);
        try {
            client.postGroup(getGroup().toJson(), errView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void pullGroup(Context ctx, View errView) {
        Client client = new Client(ctx);
        client.retrieveGroup(errView);
    }

    /**
     * Should only really have one state so make it singleton
     * @param currentUser the current client/user
     * @return the instance of the state
     */
    public static State createState(User currentUser) {
        if(stateInstance == null){
            stateInstance = new State(currentUser);
        }
        return stateInstance;
    }

}
