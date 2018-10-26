package com.example.chriszhang.roomio.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Group implements Jsonable {

    private final String groupId, groupAdminUserId;
    private String groupName;
    private List<User> members = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

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

    public void addMember(User member){
        members.add(member);
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeMember(User member){
        if(members.contains(member)){
            members.remove(member);
        }
    }

    public void deleteTask(Task task){
        if(tasks.contains(task)){
            tasks.remove(task);
        }
    }

    public Optional<User> maybeGetMember(User member){
        Optional<User> maybeUser = Optional.empty();
        return (members.contains(member))?
                Optional.of(members.get(members.indexOf(member))):
                maybeUser;
    }

    public Optional<Task> maybeGetTask(Task task){
        Optional<Task> maybeTask = Optional.empty();
        return (tasks.contains(task))?
                Optional.of(tasks.get(tasks.indexOf(task))) :
                maybeTask;
    }

    //TODO
    public void updateMember(User member){}

    public void updateTask(Task task){}



    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
