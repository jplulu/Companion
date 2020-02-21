package com.lustermaniacs.companion.models;

import java.util.UUID;          // Universally unique identifier class

//Double Checking the push and merge
public class User {
    // Instance Fields
        String username;
        String password;
        UUID id;
        Profile profile;

    // Constructors
    public User(String username, String password, UUID id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }
    public User(String username, String password, UUID id, Profile profile) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.profile = profile;
    }

    // Getters and Setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}