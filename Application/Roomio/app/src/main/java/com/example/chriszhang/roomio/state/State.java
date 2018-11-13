package com.example.chriszhang.roomio.state;

import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.User;

import java.util.HashMap;

/**
 * A client-side wrapper class for the global User/Group data for the representing
 * the current state of the application for the user in the current session.
 */
public class State {

    private Group group;
    private User currentUser;

    private static State stateInstance = null;

    private State(User currentUser){
        this.currentUser = currentUser;
    }

    public static boolean hasGroup() {
       return (stateInstance != null && stateInstance.group != null);
    }

    public static Group getGroup() {
        return getStateInstance().group;
    }

    public static User getCurrentUser() {
        return getStateInstance().currentUser;
    }

    public static State getStateInstance() {
        return stateInstance;
    }

    public static void setGroup (Group g){
        stateInstance.group = g;
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
