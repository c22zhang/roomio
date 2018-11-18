package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashSet;

/**
 * Represents a task that person needs to complete.
 */
public final class Task implements Jsonable {

    private final String taskId, assignerUserId;
    private final String dateAssigned;
    private String taskName, description, assigneeUserId;

    /**
     * General constructor for Task
     * @param taskId unique identifier of the task
     * @param assigneeUserId the userId of the person assigned the task
     * @param assignerUserId the userId of the person assigning the task
     * @param taskName the name of the task
     * @param description the task description
     * @param dateAssigned the date the task was assigned
     */
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

    // assorted getters/setters
    public String getTaskId() { return taskId; }
    public String getAssigneeUserId() { return assigneeUserId; }
    public String getAssignerUserId() { return assignerUserId; }
    public String getDateAssigned() { return dateAssigned; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public void setAssigneeUserId(String assigneeUserId) { this.assigneeUserId = assigneeUserId; }

    /**
     * Equality comparison between this task and another object.
     * @param obj the object to be compared
     * @return whether or not the object is equal to this task
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Task){
            Task tObj = (Task) obj;
            return tObj.getTaskId().equals(this.getTaskId()) &&
                    tObj.getTaskName().equals(this.getTaskName()) &&
                    tObj.getAssignerUserId().equals(this.getAssignerUserId()) &&
                    tObj.getAssigneeUserId().equals(this.getAssigneeUserId()) &&
                    tObj.getDateAssigned().equals(this.getDateAssigned());
        }
        return false;
    }

    /**
     * @return JSON string representation of this task
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
     * @return a JSON representation of this Task
     * @throws JSONException
     */
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

    /**
     * Convenience method for instantiating a Task from a JSON object
     * @param obj a JSON object representing a Task
     * @return a new Task object with the fields in the JSON object
     * @throws JsonToObjectException if required fields are missing
     * @throws JSONException if something goes wrong during JSON parsing
     */
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
