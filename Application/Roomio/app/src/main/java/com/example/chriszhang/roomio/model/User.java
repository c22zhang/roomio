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

public final class User implements Jsonable {

    private final String userId, username, name, email, password;
    private List<Notification> notifications = new ArrayList<>();
    private List<Tab> myTabs = new ArrayList<>();
    private Optional<String> adminedGroupId, householdGroupId;

    public User(
            String userId,
            String username,
            String name,
            String email,
            String password,
            Optional<String> adminedGroupId,
            Optional<String> householdGroupId){
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.adminedGroupId = adminedGroupId;
        this.householdGroupId = householdGroupId;

    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    //TODO: add some extra verification to this;
    public String getPassword() { return password; }
    public Optional<String> getAdminedGroupId() { return adminedGroupId; }
    public void setAdminedGroupId(Optional<String> adminedGroupId) { this.adminedGroupId = adminedGroupId; }
    public Optional<String> getHouseholdGroupId() { return householdGroupId; }
    public void setHouseholdGroupId(Optional<String> householdGroupId) { this.householdGroupId = householdGroupId; }
    public List<Notification> getNotifications() { return notifications; }
    public List<Tab> getMyTabs() { return myTabs; }

    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    public void addTab(Tab tab){
        this.myTabs.add(tab);
    }

    public void deleteNotification(Notification notification){
        if(notifications.contains(notification)){
            notifications.remove(notification);
        }
    }

    public void deleteTab(Tab tab){
        if(myTabs.contains(tab)){
            myTabs.remove(tab);
        }
    }

    public Optional<Notification> maybeGetNotification(Notification notification){
        Optional<Notification> maybeNote = Optional.empty();
        return (notifications.contains(notification))?
                Optional.of(notifications.get(notifications.indexOf(notification))) :
                maybeNote;
    }

    public Optional<Tab> maybeGetTab(Tab tab){
        Optional<Tab> maybeTab = Optional.empty();
        return (myTabs.contains(tab))?
                Optional.of(myTabs.get(myTabs.indexOf(tab))) :
                maybeTab;
    }

    //TODO
    public void editTab(Tab tab){}

    @Override
    public String toString() {
        try{
            return toJson().toString();
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }


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

    public static User from(JSONObject obj) throws JsonToObjectException, JSONException {
        User user;
        Set<String> required =
                Utils.requiredFieldSet(
                        "user_id",
                        "username",
                        "name",
                        "email",
                        "password",
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
                    (String) obj.get("password"),
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

}
