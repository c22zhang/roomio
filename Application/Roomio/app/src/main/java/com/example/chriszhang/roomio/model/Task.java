package com.example.chriszhang.roomio.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public final class Task implements Jsonable {

    private final String taskId, assigneeUserId, assignerUserId;
    private final String dateAssigned;
    private String taskName, description;

    public Task(
            String taskId,
            String assigneeUserId,
            String assignerUserId,
            String taskName,
            String description,
            String dateAssigned){
        this.taskId = taskId;
        this.assigneeUserId = assigneeUserId;
        this.assignerUserId = assignerUserId;
        this.taskName = taskName;
        this.description = description;
        this.dateAssigned = dateAssigned;
    }

    public String getTaskId() { return taskId; }

    public String getAssigneeUserId() { return assigneeUserId; }

    public String getAssignerUserId() { return assignerUserId; }

    public String getDateAssigned() { return dateAssigned; }

    public String getTaskName() { return taskName; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

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
        JSONObject obj = new JSONObject();
        obj.put("task_id", taskId);
        obj.put("assignee", assigneeUserId);
        obj.put("assigner", assignerUserId);
        obj.put("task_name", taskName);
        obj.put("description", description);
        obj.put("date_assigned", dateAssigned);
        return obj;
    }
}
