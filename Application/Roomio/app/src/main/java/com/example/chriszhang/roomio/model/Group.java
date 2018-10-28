package com.example.chriszhang.roomio.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a roommate group within Room.io.
 */
public final class Group implements Jsonable {

    private final String groupId, groupAdminUserId;
    private String groupName;
    private List<User> members = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Message> groupChatMessages = new ArrayList<>();

    public Group(String groupId, String groupAdminUserId, String groupName){
        this.groupId = groupId;
        this.groupAdminUserId = groupAdminUserId;
        this.groupName = groupName;
    }

    public String getGroupId() { return groupId; }
    public String getGroupAdminUserId() { return groupAdminUserId; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public List<User> getMembers() { return members; }
    public List<Task> getTasks() { return tasks; }
    public List<Message> getGroupChatMessages() { return groupChatMessages; }

    public void addMember(User member) {
        members.add(member);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addMessage(Message message) {
        groupChatMessages.add(message);
    }

    public void removeMember(User member) {
        if(members.contains(member)){
            members.remove(member);
        }
    }

    public void deleteTask(Task task) {
        if(tasks.contains(task)){
            tasks.remove(task);
        }
    }

    public Optional<User> maybeGetMember(User member) {
        Optional<User> maybeUser = Optional.empty();
        return (members.contains(member))?
                Optional.of(members.get(members.indexOf(member))):
                maybeUser;
    }

    public Optional<Task> maybeGetTask(Task task) {
        Optional<Task> maybeTask = Optional.empty();
        return (tasks.contains(task))?
                Optional.of(tasks.get(tasks.indexOf(task))) :
                maybeTask;
    }

    //TODO: implement these if necessary
    public void updateMember(User member) {}

    public void updateTask(Task task) {}


    @Override
    public String toString() {
        try{
            return toJson().toString();
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("group_id", groupId);
        object.put("group_name", groupName);
        object.put("group_admin_user_id", groupAdminUserId);

        object.put("members", generateMembersList());
        object.put("group_tasks", generateTaskList());
        object.put("messages", generateMessageList());

        return object;
    }

    private JSONArray generateMembersList() throws JSONException {
        JSONArray membersList = new JSONArray();
        for(User member: members) {
            membersList.put(member.toJson());
        }
        return membersList;
    }

    private JSONArray generateTaskList() throws JSONException {
        JSONArray taskList = new JSONArray();
        for(Task task: tasks){
            taskList.put(task.toJson());
        }
        return taskList;
    }

    private JSONArray generateMessageList() throws JSONException {
        JSONArray messages = new JSONArray();
        for(Message message: groupChatMessages){
            messages.put(message.toJson());
        }
        return messages;
    }

}
