package org.chat.messenger.dto;

import org.chat.messenger.model.NotificationType;

public class NotificationDTO {
    private NotificationType type;
    private String sender;
    private String recipient;
    private String content;

    public NotificationDTO() {}

    public NotificationDTO(NotificationType type, String sender, String recipient, String content) {
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


