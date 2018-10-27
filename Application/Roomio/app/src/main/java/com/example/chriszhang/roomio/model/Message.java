package com.example.chriszhang.roomio.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public final class Message implements Jsonable{

    private final String messageId, messageContents, senderId;
    private final String dateSent;

    public Message(
            String messageId,
            String messageContents,
            String senderId,
            String dateSent){
        this.messageId = messageId;
        this.messageContents = messageContents;
        this.senderId = senderId;
        this.dateSent = dateSent;
    }

    public String getMessageId() { return messageId; }
    public String getMessageContents() { return messageContents; }
    public String getSenderId() { return senderId; }
    public String dateSent() { return dateSent; }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("message_id", messageId);
        obj.put("message_contents", messageContents);
        obj.put("sender_id", senderId);
        obj.put("date_sent", dateSent);
        return obj;
    }

    @Override
    public String toString() {
        try{
            return toJson().toString();
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
