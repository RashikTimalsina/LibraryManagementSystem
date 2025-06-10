package main.java.org.rashiktimalsina.entities;

import java.io.Serializable;

/**
 * @author RashikTimalsina
 * @created 28/04/2025
 */

public class User implements Serializable {
    private int id;
    private String name;
    private String email;

    //id is auto-generated
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int generatedId) {
        id=generatedId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User ID: " + id + ", Name: " + name + ", Email: " + email;
    }
}