package com.movieMagnet.classes;

import static com.movieMagnet.classes.Encryption.Encrypt;

public class User {
    private String name;
    private String ageGroup;
    private String password;
    private String username;
    private String email;

    public User() {

    }

    public User(String name, String ageGroup, String password, String username, String email) {
        this.name = name;
        this.ageGroup = ageGroup;
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public User(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeGroup() {
        return this.ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = Encrypt(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " " + ageGroup + " " + password + " " + username + " " + email;
    }
}
