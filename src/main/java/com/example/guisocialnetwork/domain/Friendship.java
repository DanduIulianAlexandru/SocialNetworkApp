package com.example.guisocialnetwork.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.guisocialnetwork.domain.FriendshipStatus.*;

public class Friendship {
    private final long id;
    private long firstFriend;
    private long secondFriend;
    private LocalDateTime dateTime;
    FriendshipStatus status;

    /**
     * constructor
     * @param id id-ul prietenie, tipul Long
     * @param firstFriend id-ul primului prieten, tipul Long
     * @param secondFriend id-ul celui de-al doilea prieten, tipul Long
     * @param dateTime data la care firstFriend a devenit prieten cu secondFriend
     */
    public Friendship(long id, long firstFriend, long secondFriend, LocalDateTime dateTime) {
        this.id = id;
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
        this.dateTime = dateTime;
        this.status = PENDING;
    }

    public FriendshipStatus getStatus(){
        return this.status;
    }

    public void acceptFriendship(){
        this.status = ACCEPTED;
    }

    public void deleteFriendship(){
        this.status = DELETED;
    }

    public int statusToInt() {
        if (status == FriendshipStatus.PENDING) return 0;
        if (status == FriendshipStatus.ACCEPTED) return 1;
        return 2;
    }

    public void setStatusFromInt(int status) {
        if (status == 0) this.status = FriendshipStatus.PENDING;
        if (status == 1) this.status = FriendshipStatus.ACCEPTED;
        if (status == 2) this.status = FriendshipStatus.DELETED;

    }

    public long getId() {
        return id;
    }
    public long getFirstFriend() {
        return firstFriend;
    }
    public void setFirstFriend(long firstFriend) {
        this.firstFriend = firstFriend;
    }
    public long getSecondFriend() {
        return secondFriend;
    }
    public void setSecondFriend(long secondFriend) {
        this.secondFriend = secondFriend;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String toString() {
        return "Friendship{id=" + this.id + ", firstFriend=" + this.firstFriend + ", secondFriend=" + this.secondFriend + ", dateTime=" + this.dateTime + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship that)) return false;
        return getId() == that.getId() && getFirstFriend() == that.getFirstFriend() && getSecondFriend() == that.getSecondFriend() && getDateTime().equals(that.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstFriend(), getSecondFriend(), getDateTime());
    }
}
