package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public final class Tab implements Jsonable {

    private final String tabId, assignerUserId;
    private final String dateAssigned;
    private String tabName, reason, assigneeUserId;
    private Double amount;

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
        obj.put("tab_id", tabId);
        obj.put("tab_name", tabName);
        obj.put("reason", reason);
        obj.put("assignee", assigneeUserId);
        obj.put("assigner", assignerUserId);
        obj.put("date_assigned", dateAssigned);
        obj.put("amount", amount);
        return obj;
    }

    public static Tab from(JSONObject json) throws JsonToObjectException, JSONException {
        Set<String> requiredFields =
                Utils.requiredFieldSet(
                        "tab_id",
                        "tab_name",
                        "reason",
                        "assignee",
                        "assigner",
                        "date_assigned",
                        "amount");
        if(Utils.containsRequiredFields(json, requiredFields)){
            return new Tab(
                    (String) json.get("tab_id"),
                    (String) json.get("tab_name"),
                    (String) json.get("reason"),
                    (String) json.get("assignee"),
                    (String) json.get("assigner"),
                    (String) json.get("date_assigned"),
                    (Double) json.get("amount"));
        } else {
            throw new JsonToObjectException("Did not contain all required fields");
        }
    }
}
