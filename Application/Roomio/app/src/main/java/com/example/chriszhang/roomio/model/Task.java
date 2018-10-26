package com.example.chriszhang.roomio.model;

import org.json.JSONObject;

import java.util.Date;

public final class Task implements Jsonable {

    private final String taskId, assigneeUserId, assignedUserId;
    private final Date dateAssigned;
    private String taskName, description;

    public Task(
            String taskId,
            String assigneeUserId,
            String assignedUserId,
            String taskName,
            String description,
            Date dateAssigned){
        this.taskId = taskId;
        this.assigneeUserId = assigneeUserId;
        this.assignedUserId = assignedUserId;
        this.taskName = taskName;
        this.description = description;
        this.dateAssigned = dateAssigned;
    }

    public String getTaskId() { return taskId; }

    public String getAssigneeUserId() { return assigneeUserId; }

    public String getAssignedUserId() { return assignedUserId; }

    public Date getDateAssigned() { return dateAssigned; }

    public String getTaskName() { return taskName; }

    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
