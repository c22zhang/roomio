package com.example.chriszhang.roomio.model;

/**
 * Exception for handling errors when creating data model objects from JSON
 */
public class JsonToObjectException extends Exception {

    public JsonToObjectException(String cause) {
        super(cause);
    }
}
