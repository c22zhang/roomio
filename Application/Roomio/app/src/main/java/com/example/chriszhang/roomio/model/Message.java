package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Set;

/**
 * Represents a groupchat message
 */
public final class Message implements Jsonable{

    private final String messageId, messageContents, senderId;
    private final String dateSent;

    /**
     * General constructor for a gropuchat message
     * @param messageId unique identifier for a message
     * @param messageContents the text contents of a message
     * @param senderId the userid of the person who sent the message
     * @param dateSent the date that the message was sent
     */
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

    // assorted getters
    public String getMessageId() { return messageId; }
    public String getMessageContents() { return messageContents; }
    public String getSenderId() { return senderId; }
    public String getDateSent() { return dateSent; }

    /**
     *
     * @return a JSON representation of this message
     * @throws JSONException if JSON parsing goes wrong
     */
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("message_id", messageId);
        obj.put("message_contents", messageContents);
        obj.put("sender_id", senderId);
        obj.put("date_sent", dateSent);
        return obj;
    }

    /**
     * @return JSON string for this object
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
     * Convenience method for instantiating a Message via JSONObject
     * @param obj JSONObject representing a Message
     * @return a new Message made with fields from the JSONObject
     * @throws JsonToObjectException if something goes wrong with parsing JSON
     * @throws JSONException if required fields are missing
     */
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
