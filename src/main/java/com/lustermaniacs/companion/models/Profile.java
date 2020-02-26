package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

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
    private String[] surveyResults;
    private List<UUID> sysmatchedUsers;

    // Constructor
    public Profile(@JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("age") Byte age,
                   @JsonProperty("gender") char gender,
                   @JsonProperty("profilePic") List<String> profilePic,
                   @JsonProperty("location") String location,
                   @JsonProperty("maxDistance") int maxDistance,
                   @JsonProperty("bio") String bio,
                   @JsonProperty("surveyResults") String[] surveyResults,
                   @JsonProperty("sysmatchedUsers") List<UUID> sysmatchedUsers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.profilePic = profilePic;
        this.location = location;
        this.maxDistance = maxDistance;
        this.bio = bio;
        this.surveyResults = surveyResults;
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

    public String[] getSurveyResults() {
        return surveyResults;
    }

    public void setSurveyResults(String[] surveyResults) {
        this.surveyResults = surveyResults;
    }

    public List<UUID> getSysmatchedUsers() {
        return sysmatchedUsers;
    }

    public void setSysmatchedUsers(List<UUID> sysmatchedUsers) {
        this.sysmatchedUsers = sysmatchedUsers;
    }
}