package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class Profile {

    // Instance Fields
    private String firstName;
    private String lastName;
    private Byte age;
    private char gender;
    private List<String> profilePic;
    private String location;
    private int maxDistance;
    private String bio;
    private List<String> sysmatchedUsers;

    // Constructor
    public Profile(@JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("age") Byte age,
                   @JsonProperty("gender") char gender,
                   @JsonProperty("profilePic") List<String> profilePic,
                   @JsonProperty("location") String location,
                   @JsonProperty("maxDistance") int maxDistance,
                   @JsonProperty("bio") String bio,
                   @JsonProperty("sysmatchedUsers") List<String> sysmatchedUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.profilePic = profilePic;
        this.location = location;
        this.maxDistance = maxDistance;
        this.bio = bio;
        this.sysmatchedUsers = sysmatchedUsers;
    }

    public Profile() {

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

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public List<String> getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(List<String> profilePic) {
        this.profilePic = profilePic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getSysmatchedUsers() {
        return sysmatchedUsers;
    }

    public void setSysmatchedUsers(List<String> sysmatchedUsers) {
        this.sysmatchedUsers = sysmatchedUsers;
    }

    // Method to add a user to someone's sysmatched list, which is stored as list of usernames
    public void addSysmatchedUser(String userName){
        this.sysmatchedUsers.add(userName);
    }

    // Method to remove a user from someone's sysmatched list
    public void removeSysmatchedUser(String userName){
        this.sysmatchedUsers.remove(userName);
    }
}