package com.example.chriszhang.roomio.model;

import org.json.JSONObject;

import java.util.Date;

public final class Message implements Jsonable{

    private final String messageId, messageContents, senderId;
    private final Date dateSent;

    public Message(
            String messageId,
            String messageContents,
            String senderId,
            Date dateSent){
        this.messageId = messageId;
        this.messageContents = messageContents;
        this.senderId = senderId;
        this.dateSent = dateSent;
    }

    public String getMessageId() {return messageId;}
    public String getMessageContents(){return messageContents;}
    public String getSenderId(){return senderId;}
    public Date dateSent(){return dateSent;}

    @Override
    public JSONObject toJson() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
