package domain;

import java.util.Objects;

public class User {
    private final long id;
    private String firstName;
    private String lastName;

    /**
     * constructor
     * @param id id-ul utilizatorului, tipul Long
     * @param firstName prenumele utilizatorului, tipul String
     * @param lastName numele utilizatoruilui, tipul String
     */
    public User(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{id=" + this.id + "'firstName='" + this.firstName + "', lastName='"
                + this.lastName;
    }

    /**
     * verifica daca doi utilizatori sunt egali
     * @param o obiectul pe care dorim sa il comparam cu cel curent, tipul Object
     * @return - true - daca utilizatorii sunt egali
     *         -false - daca utilizatorii nu sunt egali
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof User that) {
            return this.id == that.id;
        } else {
            return false;
        }
    }
    /**
     * returneaza hashCode-ul unui utilizator
     * @return -hashCode-ul, tipul Integer
     */
    public int hashCode() {
        return Objects.hash(this.getId(), this.getFirstName(), this.getLastName());
    }
}
