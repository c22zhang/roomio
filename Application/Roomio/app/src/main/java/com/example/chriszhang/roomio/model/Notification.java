package com.example.chriszhang.roomio.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public final class Notification implements Jsonable {

    public enum Type {
        ANONYMOUS_BUTTON(false),
        CLEAR_TAB_REQ(true),
        CLEAR_TASK_REQ(true),
        REQ_ACCEPTANCE(false),
        ASSIGNMENT(false);

        private boolean isClearable;
        Type(boolean isClearable){
            this.isClearable = isClearable;
        }
    }

    private final String notificationId, toUserId, fromUserId;
    private final boolean isClearable;
    private final Type notificationType;
    private HashMap<Type, String> messageMap = new HashMap<Type, String>();

    public Notification(
            String notificationId,
            String toUserId,
            String fromUserId,
            Type notificationType){
        this.notificationId = notificationId;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.notificationType = notificationType;
        this.isClearable = notificationType.isClearable;
        initializeMessages();
    }

    public String getNotificationId() { return notificationId; }
    public String getMessage() { return messageMap.get(notificationType); }
    public String getToUserId() { return toUserId; }
    public String getFromUserId() { return fromUserId; }
    public boolean isClearable() { return isClearable; }
    public Type getNotificationType() { return notificationType; }



    @Override
    public String toString() {
        try{
            return toJson().toString();
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }    }

    @Override
    public JSONObject toJson() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("notification_id", notificationId);
        obj.put("to_user_id", toUserId);
        obj.put("from_user_id", fromUserId);
        obj.put("notification_type", notificationType.toString());
        return obj;
    }

    //TODO: make these messages better once you write the means to
    private void initializeMessages(){
        messageMap.put(
                Type.ANONYMOUS_BUTTON,
                "Someone has anonymously sent you a message: message_here");
        messageMap.put(
                Type.ASSIGNMENT,
                "Someone has assigned you something");
        messageMap.put(
                Type.CLEAR_TAB_REQ,
                "Someone has requested you to clear a tab you assigned.");
        messageMap.put(
                Type.CLEAR_TASK_REQ,
                "Someone has requested you to clear a task you assigned them.");
        messageMap.put(
                Type.REQ_ACCEPTANCE,
                "Someone has cleared your task/tab.");
    }
}
