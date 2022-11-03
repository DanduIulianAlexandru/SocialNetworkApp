package domain;

import java.util.Objects;

public class Friendship {
    private final long id;
    private long firstFriend;
    private long secondFriend;

    /**
     * constructor
     * @param id id-ul prietenie, tipul Long
     * @param firstFriend id-ul primului prieten, tipul Long
     * @param secondFriend id-ul celui de-al doilea prieten, tipul Long
     */
    public Friendship(long id, long firstFriend, long secondFriend) {
        this.id = id;
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
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
    public String toString() {
        return "Friendship{id=" + this.id + ", firstFriend=" + this.firstFriend + ", secondFriend=" + this.secondFriend + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Friendship friendship = (Friendship)o;
            return friendship.getFirstFriend() == this.firstFriend && friendship.getSecondFriend() == this.secondFriend || friendship.getFirstFriend() == this.secondFriend && friendship.getSecondFriend() == this.firstFriend;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.id, this.firstFriend, this.secondFriend);
    }
}
