package org.chat.messenger.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_one_id")
    private Account userOne;
    @ManyToOne
    @JoinColumn(name = "user_two_id")
    private Account userTwo;
    private RoomStatus userOneStatus;
    private RoomStatus userTwoStatus;

    public Room() {
    }

    public Room(String roomId, Account userOne, Account userTwo, RoomStatus userOneStatus, RoomStatus userTwoStatus) {
        this.id = roomId;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.userOneStatus = userOneStatus;
        this.userTwoStatus = userTwoStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String roomId) {
        this.id = roomId;
    }

    public Account getUserOne() {
        return userOne;
    }

    public void setUserOne(Account userOne) {
        this.userOne = userOne;
    }

    public Account getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(Account userTwo) {
        this.userTwo = userTwo;
    }

    public RoomStatus getUserOneStatus() {
        return userOneStatus;
    }

    public void setUserOneStatus(RoomStatus userOneStatus) {
        this.userOneStatus = userOneStatus;
    }

    public RoomStatus getUserTwoStatus() {
        return userTwoStatus;
    }

    public void setUserTwoStatus(RoomStatus userTwoStatus) {
        this.userTwoStatus = userTwoStatus;
    }
}
