package com.example.chatBE.entities;

import java.util.Date;

public class WebSocketMessage {

    private String message;
    private String receiver;
    private String sender;
    private String type;

    public String getMessage() {
        return message;
    }

    public String getReceiver() { return receiver; }

    public String getSender() { return sender; }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
