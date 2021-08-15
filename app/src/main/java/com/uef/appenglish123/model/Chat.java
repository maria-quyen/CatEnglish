package com.uef.appenglish123.model;

public class Chat {
    private String sender, receive, message;

    public Chat(String sender, String receive, String message) {
        this.sender = sender;
        this.receive = receive;
        this.message = message;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
