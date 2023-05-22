package org.chat.messenger.dto;

import org.chat.messenger.model.RoomStatus;

public class RoomDTO {
    private String user;
    private RoomStatus status;

    public RoomDTO() {}

    public RoomDTO(String user, RoomStatus status) {
        this.user = user;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}


