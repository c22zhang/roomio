package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public final class Notification implements Jsonable {

    /**
     * Enum representing the five types of possible notifications.
     */
    public enum Type {
        //Anonymous button press
        ANONYMOUS_BUTTON(false),
        //Request to clear a tab
        CLEAR_TAB_REQ(true),
        //request to clear a task
        CLEAR_TASK_REQ(true),
        //your request has been accepted
        REQ_ACCEPTANCE(false),
        //your request has been rejected
        REQ_REJECTION(false),
        //you have been assigned a task
        ASSIGNMENT(false);

        private boolean isClearable;
        Type(boolean isClearable){
            this.isClearable = isClearable;
        }
    }

    private final String notificationId, toUserId, fromUserId, message;
    private final Optional<Jsonable> potentialClear;
    private final boolean isClearable;
    private final Type notificationType;

    /**
     * General constructor for notification
     * @param notificationId unique identifier for the notification
     * @param toUserId userId of the person receiving it
     * @param fromUserId userId of the person sending it
     * @param notificationType the Notification.Type of this notification
     */
    public Notification(
            String notificationId,
            String message,
            String toUserId,
            String fromUserId,
            Type notificationType,
            Optional<Jsonable> potentialClear){
        this.notificationId = notificationId;
        this.message = message;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.notificationType = notificationType;
        this.isClearable = notificationType.isClearable;
        this.potentialClear = potentialClear;
    }

    // assorted getters
    public String getNotificationId() { return notificationId; }
    public String getMessage() { return message; }
    public String getToUserId() { return toUserId; }
    public String getFromUserId() { return fromUserId; }
    public boolean isClearable() { return isClearable; }
    public Type getNotificationType() { return notificationType; }
    public Optional<Jsonable> getPotentialClear() {
        return potentialClear;
    }

    /**
     * @return JSON string representing this notification
     */
    @Override
    public String toString() {
        try{
            return toJson().toString();
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return JSONObject conversion of this object
     * @throws JSONException if JSON parsing goes wrong
     */
    @Override
    public JSONObject toJson() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("notification_id", notificationId);
        obj.put("message", message);
        obj.put("to_user_id", toUserId);
        obj.put("from_user_id", fromUserId);
        obj.put("notification_type", notificationType.toString());

        if(potentialClear.isPresent()){
            obj.put("potential_clear", potentialClear.get().toJson());
        }
        return obj;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Notification){
            Notification not = (Notification) obj;
            return notificationId.equals( not.notificationId) &&
                    message.equals(not.message) &&
                    notificationType.equals(not.notificationType) &&
                    toUserId.equals(not.toUserId) &&
                    fromUserId.equals(not.fromUserId);
        }
        return false;
    }

    /**
     * Convenience method for instantiating a Notification via JSONObject
     * @param obj JSONObject representing a Notification
     * @return a new Notification made with fields from the JSONObject
     * @throws JsonToObjectException if something goes wrong with parsing JSON
     * @throws JSONException if required fields are missing
     */
    public static Notification from(JSONObject obj) throws JsonToObjectException, JSONException {
        Set<String> required =
                Utils.requiredFieldSet(
                        "notification_id",
                        "message",
                        "to_user_id",
                        "from_user_id",
                        "notification_type");
        if(Utils.containsRequiredFields(obj, required)){
            Optional<Jsonable> potentialClear = Optional.empty();
            Jsonable modelObj = getModelObject(obj);
            if(modelObj != null) {
                potentialClear = Optional.of(modelObj);
            }
            return new Notification(
                    (String) obj.get("notification_id"),
                    (String) obj.get("message"),
                    (String) obj.get("to_user_id"),
                    (String) obj.get("from_user_id"),
                    Type.valueOf((String) obj.get("notification_type")),
                    potentialClear);
        } else {
            throw new JsonToObjectException("Missing required fields");
        }
    }

    private static Jsonable getModelObject(JSONObject obj)
            throws JsonToObjectException, JSONException{
        if(obj.has("potential_clear")){
          JSONObject nested = obj.getJSONObject("potential_clear");
          if(nested.has("task_id")){
              return Task.from(nested);
          }
          if (nested.has("tab_id")) {
              return Tab.from(nested);
          }
        }
        return null;
    }
}
