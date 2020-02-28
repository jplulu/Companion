package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;          // Universally unique identifier class

public class User {
    // Instance Fields
        private String username;
        private String password;
        private UUID id;
        private Profile profile;
        private SurveyResults surveyResults;

        //Constructors
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("id") UUID id,
                @JsonProperty("profile") Profile profile,
                @JsonProperty("surveyResults") SurveyResults surveyResults) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.profile = profile;
        this.surveyResults = surveyResults;
    }

    // Getters and Setters

    public SurveyResults getSurveyResults() {
        return surveyResults;
    }

    public void setSurveyResults(SurveyResults surveyResults) {
        this.surveyResults = surveyResults;
    }

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