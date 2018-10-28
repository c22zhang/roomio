package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;

public final class Task implements Jsonable {

    private final String taskId, assignerUserId;
    private final String dateAssigned;
    private String taskName, description, assigneeUserId;

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
    public void setAssigneeUserId(String assigneeUserId) { this.assigneeUserId = assigneeUserId; }

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

    public static Task from(JSONObject obj) throws JsonToObjectException, JSONException {
        HashSet<String> required =
                Utils.requiredFieldSet(
                        "task_id",
                        "assignee",
                        "assigner",
                        "task_name",
                        "description",
                        "date_assigned");
        if(Utils.containsRequiredFields(obj, required)){
            return new Task(
                    (String) obj.get("task_id"),
                    (String) obj.get("assignee"),
                    (String) obj.get("assigner"),
                    (String) obj.get("task_name"),
                    (String) obj.get("description"),
                    (String) obj.get("date_assigned"));
        } else {
            throw new JsonToObjectException("Did not have required fields.");
        }
    }
}
