package com.lustermaniacs.companion.models;

public class User {
    // Instance Fields
        String username;
        String password;
        int uuid;
        Profile profile;

    public User(String username, String password, int uuid) {
        this.username = username;
        this.password = password;
        this.uuid = uuid;
    }
    public User(String username, String password, int uuid, Profile profile) {
        this.username = username;
        this.password = password;
        this.uuid = uuid;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUuid() {
        return uuid;
    }

    public Profile getProfile() {
        return profile;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}