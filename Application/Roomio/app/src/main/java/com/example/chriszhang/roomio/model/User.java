package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a Room.io user.
 */
public final class User implements Jsonable {

    private final String userId, username, name, email;
    private List<Notification> notifications = new ArrayList<>();
    private List<Tab> myTabs = new ArrayList<>();
    private Optional<String> adminedGroupId, householdGroupId;

    /**
     * General constructor for user
     * @param userId unique identifier for user
     * @param username unique username for user
     * @param name the user's actual name
     * @param email the user's email
     * @param adminedGroupId the groupId of the group this user admins, if it exists
     * @param householdGroupId the groupId of the household this user belongs to
     */
    public User(
            String userId,
            String username,
            String name,
            String email,
            Optional<String> adminedGroupId,
            Optional<String> householdGroupId){
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.adminedGroupId = adminedGroupId;
        this.householdGroupId = householdGroupId;

    }

    // assorted getters/setters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Optional<String> getAdminedGroupId() { return adminedGroupId; }
    public void setAdminedGroupId(Optional<String> adminedGroupId) { this.adminedGroupId = adminedGroupId; }
    public Optional<String> getHouseholdGroupId() { return householdGroupId; }
    public void setHouseholdGroupId(Optional<String> householdGroupId) { this.householdGroupId = householdGroupId; }
    public List<Notification> getNotifications() { return notifications; }
    public List<Tab> getMyTabs() { return myTabs; }

    /**
     * Adds the notification to the User
     * @param notification the notification to be added
     */
    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    /**
     * Adds the tab to the User
     * @param tab the tab to be added
     */
    public void addTab(Tab tab){
        this.myTabs.add(tab);
    }

    /**
     * deletes the notification from the User
     * @param notification the notification to be deleted
     */
    public void deleteNotification(Notification notification){
        if(notifications.contains(notification)){
            notifications.remove(notification);
        }
    }

    /**
     * deletes the tab from the user
     * @param tab the tab to be deleted
     */
    public void deleteTab(Tab tab){
        if(myTabs.contains(tab)){
            myTabs.remove(tab);
        }
    }

    //TODO: implement this

    /**
     * Edits the tab with tabId with the fields in tab if it exists
     * @param tabId the id of the tab to be edited
     * @param tab where to get the updated fields from
     */
    public void editTab(String tabId, Tab tab){}

    @Override
    public String toString() {
        try{
            return toJson().toString();
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return JSON object representation of the User
     * @throws JSONException if JSON parsing goes wrong
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("user_id", userId);
        obj.put("username", username);
        obj.put("name", name);
        obj.put("email", email);

        if(adminedGroupId.isPresent()){
            obj.put("admined_group_id", adminedGroupId.get());
        }

        if(householdGroupId.isPresent()){
            obj.put("member_group_id", householdGroupId.get());
        }

        obj.put("involved_tabs", generateTabsList());
        obj.put("notifications", generateNotificationsList());
        return obj;
    }

    private JSONArray generateTabsList() throws JSONException {
        JSONArray array = new JSONArray();
        for(Tab tab : myTabs){
            array.put(tab.toJson());
        }
        return array;
    }

    private JSONArray generateNotificationsList() throws JSONException {
        JSONArray array = new JSONArray();
        for(Notification notification: notifications){
            array.put(notification.toJson());
        }
        return array;
    }

    /**
     * Convenience method for instantiating a User from a JSON object
     * @param obj a JSON object representing a User
     * @return a new User object with the fields in the JSON object
     * @throws JsonToObjectException if required fields are missing
     * @throws JSONException if something goes wrong during JSON parsing
     */
    public static User from(JSONObject obj) throws JsonToObjectException, JSONException {
        User user;
        Set<String> required =
                Utils.requiredFieldSet(
                        "user_id",
                        "username",
                        "name",
                        "email",
                        "involved_tabs",
                        "notifications");
        Optional<String> adminedGroupId = Optional.empty();
        Optional<String> houseHoldGroupId = Optional.empty();
        if(Utils.containsRequiredFields(obj, required)){
            if(obj.has("admined_group_id")){
                adminedGroupId = Optional.of((String) obj.get("admined_group_id"));
            }
            if(obj.has("member_group_id")){
                houseHoldGroupId = Optional.of((String) obj.get("member_group_id"));
            }
            JSONArray tabs = obj.getJSONArray("involved_tabs");
            JSONArray notifications = obj.getJSONArray("notifications");

            user = new User(
                    (String) obj.get("user_id"),
                    (String) obj.get("username"),
                    (String) obj.get("name"),
                    (String) obj.get("email"),
                    adminedGroupId,
                    houseHoldGroupId);
            user = parseNotifications(notifications, user);
            user = parseTabs(tabs, user);
            return user;
        } else {
            throw new JsonToObjectException("Missing required fields");
        }
    }

    private static User parseNotifications(JSONArray arr, User user)
            throws JSONException, JsonToObjectException {
        for(int i = 0; i < arr.length(); i++){
            user.addNotification(Notification.from((JSONObject) arr.get(i)));
        }
        return user;
    }

    private static User parseTabs(JSONArray arr, User user)
        throws JSONException, JsonToObjectException {
        for (int i = 0; i < arr.length(); i++){
            user.addTab(Tab.from((JSONObject) arr.get(i)));
        }
        return user;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User other = (User) obj;
            return userId == other.getUserId() &&
                    username == other.getUsername() &&
                    email == other.getEmail() &&
                    name == other.getName();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * result + userId.hashCode();
        result += 19 * result + username.hashCode();
        result += 23 * result + email.hashCode();
        result += 41 * result + name.hashCode();
        return result;
    }
}
