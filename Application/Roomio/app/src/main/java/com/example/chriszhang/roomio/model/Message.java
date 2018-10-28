package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Set;

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
    public String getDateSent() { return dateSent; }

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

    public static Message from(JSONObject obj) throws JsonToObjectException, JSONException {
        Set<String> required =
                Utils.requiredFieldSet(
                        "message_id",
                        "message_contents",
                        "sender_id",
                        "date_sent");
        if(Utils.containsRequiredFields(obj, required)){
            return new Message(
                    (String) obj.get("message_id"),
                    (String) obj.get("message_contents"),
                    (String) obj.get("sender_id"),
                    (String) obj.get("date_sent"));
        } else {
            throw new JsonToObjectException("Missing required fields");
        }
    }
}
