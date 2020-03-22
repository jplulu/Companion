package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Profile {

    // Instance Fields
    private String firstName;
    private String lastName;
    private int age;
    private char gender;
    private List<String> profilePic;
    private String location;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double longitude;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double latitude;
    private String bio;

    public Profile(String firstName, String lastName, int age, char gender, List<String> profilePic, String location, double longitude, double latitude, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.profilePic = profilePic;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bio = bio;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}