package com.example.chriszhang.roomio.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


}
