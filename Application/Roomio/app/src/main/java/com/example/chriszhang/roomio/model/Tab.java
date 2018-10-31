package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Represents a tab (money exchange that needs to be paid off)
 */
public final class Tab implements Jsonable {

    private final String tabId, assignerUserId;
    private final String dateAssigned;
    private String tabName, reason, assigneeUserId;
    private Double amount;

    /**
     * General constructor
     * @param tabId Unique identifier for the tab
     * @param tabName The name of the tab
     * @param reason Reason for owing money
     * @param assigneeUserId Person owing money to
     * @param assignerUserId Person owed money
     * @param dateAssigned Date the tab was assigned
     * @param amount monetary amount
     */
    public Tab(
            String tabId,
            String tabName,
            String reason,
            String assigneeUserId,
            String assignerUserId,
            String dateAssigned,
            Double amount){
        this.tabId = tabId;
        this.tabName = tabName;
        this.reason = reason;
        this.assigneeUserId = assigneeUserId;
        this.assignerUserId = assignerUserId;
        this.dateAssigned = dateAssigned;
        this.amount = amount;
    }

    // assorted getters/setters
    public String getTabId() { return tabId; }
    public String getAssignerUserId() { return assignerUserId; }
    public String getAssigneeUserId() { return assigneeUserId; }
    public String getDateAssigned() { return dateAssigned; }
    public String getTabName() { return tabName; }
    public void setTabName(String tabName) { this.tabName = tabName; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setAssigneeUserId(String assigneeUserId) { this.assigneeUserId = assigneeUserId; }


    /**
     * @return the JSON string represented by this tab.
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
     * Function that supports conversion to JSON format.
     * @return a JSONObject with the fields of this Tab
     * @throws JSONException if something goes wrong during JSON parsing
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("tab_id", tabId);
        obj.put("tab_name", tabName);
        obj.put("reason", reason);
        obj.put("assignee", assigneeUserId);
        obj.put("assigner", assignerUserId);
        obj.put("date_assigned", dateAssigned);
        obj.put("amount", amount);
        return obj;
    }

    /**
     * Convenience method for instantiating a Tab from a JSON object
     * @param obj a JSON object representing a tab
     * @return a new tab object with the fields in the JSON object
     * @throws JsonToObjectException if required fields are missing
     * @throws JSONException if something goes wrong during JSON parsing
     */
    public static Tab from(JSONObject obj) throws JsonToObjectException, JSONException {
        Set<String> requiredFields =
                Utils.requiredFieldSet(
                        "tab_id",
                        "tab_name",
                        "reason",
                        "assignee",
                        "assigner",
                        "date_assigned",
                        "amount");
        if(Utils.containsRequiredFields(obj, requiredFields)){
            return new Tab(
                    (String) obj.get("tab_id"),
                    (String) obj.get("tab_name"),
                    (String) obj.get("reason"),
                    (String) obj.get("assignee"),
                    (String) obj.get("assigner"),
                    (String) obj.get("date_assigned"),
                    (Double) obj.get("amount"));
        } else {
            throw new JsonToObjectException("Did not contain all required fields");
        }
    }
}
