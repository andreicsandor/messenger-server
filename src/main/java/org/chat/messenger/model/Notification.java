package org.chat.messenger.model;

public class Notification {
    private String sender;

    public Notification() {}

    public Notification(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
