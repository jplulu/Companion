package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {

    // Instance Fields
    String firstName;
    String lastName;
    Byte age;
    char gender;
    String[] profilePic;
    String location;
    int maxDistance;
    String bio;
    int[] surveyResults;
    int[] sysmatchedUsers;

    // Constructor
    public Profile(@JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("age") Byte age,
                   @JsonProperty("gender") char gender,
                   @JsonProperty("profilePic") String[] profilePic,
                   @JsonProperty("location") String location,
                   @JsonProperty("maxDistance") int maxDistance,
                   @JsonProperty("bio") String bio,
                   @JsonProperty("surveyResults") int[] surveyResults,
                   @JsonProperty("sysmatchedUsers") int[] sysmatchedUsers) {
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

    public String[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String[] profilePic) {
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

    public int[] getSurveyResults() {
        return surveyResults;
    }

    public void setSurveyResults(int[] surveyResults) {
        this.surveyResults = surveyResults;
    }

    public int[] getSysmatchedUsers() {
        return sysmatchedUsers;
    }

    public void setSysmatchedUsers(int[] sysmatchedUsers) {
        this.sysmatchedUsers = sysmatchedUsers;
    }
}