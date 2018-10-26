package com.example.chriszhang.roomio.model;

import org.json.JSONObject;

import java.util.Date;

public final class Tab implements Jsonable {

    private final String tabId, assigneeUserId, assignedUserId;
    private final Date dateAssigned;
    private String tabName, reason;
    private Double amount;

    public Tab(
            String tabId,
            String tabName,
            String reason,
            String assigneeUserId,
            String assignedUserId,
            Date dateAssigned,
            Double amount){
        this.tabId = tabId;
        this.tabName = tabName;
        this.reason = reason;
        this.assigneeUserId = assigneeUserId;
        this.assignedUserId = assignedUserId;
        this.dateAssigned = dateAssigned;
        this.amount = amount;
    }

    public String getTabId() { return tabId; }

    public String getAssignedUserId() { return assignedUserId; }

    public String getAssigneeUserId() { return assigneeUserId; }

    public Date getDateAssigned() { return dateAssigned; }

    public String getTabName() { return tabName; }

    public void setTabName(String tabName) { this.tabName = tabName; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJson() {
        return null;
    }
}
