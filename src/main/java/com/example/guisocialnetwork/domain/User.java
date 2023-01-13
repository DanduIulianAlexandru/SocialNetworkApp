package com.example.guisocialnetwork.domain;

import java.util.Objects;

public class User {
    private final long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /**
     * constructor
     * @param id id-ul utilizatorului, tipul Long
     * @param username username-ul utilizatorului, tipul String
     * @param firstName prenumele utilizatorului, tipul String
     * @param lastName numele utilizatoruilui, tipul String
     * @param email email-ul utilizatorului, tipul String
     * @param password parola contului
     */
    public User(long id, String username, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * verifica daca doi utilizatori sunt egali
     * @param o obiectul pe care dorim sa il comparam cu cel curent, tipul Object
     * @return - true - daca utilizatorii sunt egali
     *         -false - daca utilizatorii nu sunt egali
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword());
    }

    /**
     * returneaza hashCode-ul unui utilizator
     * @return -hashCode-ul, tipul Integer
     */
    public int hashCode() {
        return Objects.hash(this.getId(), this.getFirstName(), this.getLastName());
    }
}
