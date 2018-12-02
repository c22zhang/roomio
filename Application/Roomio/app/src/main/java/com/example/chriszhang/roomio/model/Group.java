package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a roommate group within Room.io.
 */
public final class Group implements Jsonable {

    private final String groupId, groupAdminUserId;
    private String groupName;
    private List<User> members = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Message> groupChatMessages = new ArrayList<>();
    private HashMap<String, User> idsMap = new HashMap<>();
    private HashMap<String, User> usernameMap = new HashMap<>();
    private HashMap<User, String> userToIdMap = new HashMap<>();

    /**
     * General constructor for Group
     * @param groupId unique identifier for a group
     * @param groupAdminUserId the user id of the creator/administrator of this group
     * @param groupName the name of the group
     */
    public Group(String groupId, String groupAdminUserId, String groupName){
        this.groupId = groupId;
        this.groupAdminUserId = groupAdminUserId;
        this.groupName = groupName;
    }

    public Group(String groupId, String groupAdminUserId){
        this.groupId = groupId;
        this.groupAdminUserId = groupAdminUserId;
        this.groupName = groupId;
    }

    // assorted getters/setters
    public String getGroupId() { return groupId; }
    public String getGroupAdminUserId() { return groupAdminUserId; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public List<User> getMembers() { return members; }
    public List<Task> getTasks() { return tasks; }
    public List<Message> getGroupChatMessages() { return groupChatMessages; }

    /**
     * @param id a user's userid
     * @return a user associated from the given userid, if it exists
     */
    public Optional<User> getMemberFromId(String id){
        return (idsMap.containsKey(id))?
                Optional.of(idsMap.get(id)) : Optional.<User>empty();
    }

    public Optional<User> getMemberFromUsername(String username) {
        return (usernameMap.containsKey(username))?
                Optional.of(usernameMap.get(username)) : Optional.<User>empty();
    }

    /**
     * Adds a member to the group
     * @param member the member to be added
     */
    public void addMember(User member) {
        member.setHouseholdGroupId(Optional.of(groupId));
        members.add(member);
        idsMap.put(member.getUserId(), member);
        usernameMap.put(member.getUsername(), member);
        userToIdMap.put(member, member.getUserId());
    }

    /**
     * Adds a task to the group
     * @param task the task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Adds a message to the group's chat
     * @param message message to be added
     */
    public void addMessage(Message message) {
        groupChatMessages.add(message);
    }

    /**
     * removes a member from the group
     * @param member member to be removed
     */
    public void removeMember(User member) {
        if(members.contains(member)){
            members.remove(member);
        }
    }

    /**
     * @param member a user to get an id from
     * @return the id of the provided user
     */
    public String getId(User member){
        return userToIdMap.get(member);
    }

    /**
     * removes a task from the task list
     * @param task the task to be removed
     */
    public void deleteTask(Task task) {
        if(tasks.contains(task)){
            tasks.remove(task);
        }
    }

    //TODO: implement these if necessary

    /**
     * Will update the member with memberId in the Group with the fields in member if it exists.
     * @param memberId the id of the member to be updated
     * @param member the member to get fields from
     */
    public void updateMember(String memberId, User member) {}

    /**
     * Will update the task with taskId in the Group with the fields in task if it exists
     * @param taskId the id of the task to be updated
     * @param task the task to get fields from
     */
    public void updateTask(String taskId, Task task) {}


    /**
     * @return a JSON string of the object
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
     * @return JSONObject representation of the group
     * @throws JSONException if JSON parsing goes wrong
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("group_id", groupId);
        object.put("group_admin_user_id", groupAdminUserId);

        object.put("members", generateMembersList());
        object.put("group_tasks", generateTaskList());
        object.put("messages", generateMessageList());

        return object;
    }


    /**
     * Convenience method for instantiating a Group from a JSON object
     * @param obj JSON object representing a group
     * @return a new Group containing fields from the JSON object
     * @throws JSONException if JSON parsing goes wrong
     * @throws JsonToObjectException if required fields are missing from obj
     */
    public static Group from(JSONObject obj) throws JSONException, JsonToObjectException {
        Group group;
        Set<String> required =
                Utils.requiredFieldSet(
                        "group_id",
                        "group_admin_user_id",
                        "members",
                        "groupTasks",
                        "messages");
        if(Utils.containsRequiredFields(obj, required)){
            JSONArray members = obj.getJSONArray("members");
            JSONArray tasks = obj.getJSONArray("groupTasks");
            JSONArray messages = obj.getJSONArray("messages");
            group = new Group(
                    (String) obj.get("group_id"),
                    (String) obj.get("group_admin_user_id"));
            group = parseMembers(group, members);
            group = parseMessages(group, messages);
            group = parseTasks(group, tasks);
            return group;
        } else {
            throw new JsonToObjectException("Missing required fields");
        }
    }

    private static Group parseMessages(Group group, JSONArray array)
        throws JsonToObjectException, JSONException {
        for(int i = 0; i < array.length(); i++) {
            group.addMessage(Message.from((JSONObject) array.get(i)));
        }
        return group;
    }

    private static Group parseTasks(Group group, JSONArray array)
            throws JsonToObjectException, JSONException {
        for(int i = 0; i < array.length(); i++) {
            group.addTask(Task.from((JSONObject) array.get(i)));
        }
        return group;
    }

    private static Group parseMembers(Group group, JSONArray array)
            throws JsonToObjectException, JSONException {
        for(int i = 0; i < array.length(); i++) {
            group.addMember(User.from((JSONObject) array.get(i)));
        }
        return group;
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
