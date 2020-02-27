package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SurveyResults{
    //Instance Fields
    List<String> sportsAnswers;
    List<String> foodAnswers;
    List<String> musicAnswers;
    List<String> hobbyAnswers;
    byte personalityType;
    byte likesAnimals;
    byte genderPreference;
    int maxAge;
    int minAge;

    public SurveyResults(@JsonProperty("sportsAnswers") List <String> sportsAnswers,
                         @JsonProperty("foodAnswers") List<String> foodAnswers,
                         @JsonProperty("musicAnswers") List<String> musicAnswers,
                         @JsonProperty("hobbyAnswers")List<String> hobbyAnswers,
                         @JsonProperty("personalityType") byte personalityType,
                         @JsonProperty("likesAnimals") byte likesAnimals,
                         @JsonProperty("genderPreference") byte genderPreference,
                         @JsonProperty("maxAge") int maxAge,
                         @JsonProperty("minAge") int minAge) {
        this.sportsAnswers = sportsAnswers;
        this.foodAnswers = foodAnswers;
        this.musicAnswers = musicAnswers;
        this.hobbyAnswers = hobbyAnswers;
        this.personalityType = personalityType;
        this.likesAnimals = likesAnimals;
        this.genderPreference = genderPreference;
        this.maxAge = maxAge;
        this.minAge = minAge;
    }

    public List<String> getSportsAnswers() {
        return sportsAnswers;
    }

    public void setSportsAnswers(List<String> sportsAnswers) {
        this.sportsAnswers = sportsAnswers;
    }

    public List<String> getFoodAnswers() {
        return foodAnswers;
    }

    public void setFoodAnswers(List<String> foodAnswers) {
        this.foodAnswers = foodAnswers;
    }

    public List<String> getMusicAnswers() {
        return musicAnswers;
    }

    public void setMusicAnswers(List<String> musicAnswers) {
        this.musicAnswers = musicAnswers;
    }

    public List<String> getHobbyAnswers() {
        return hobbyAnswers;
    }

    public void setHobbyAnswers(List<String> hobbyAnswers) {
        this.hobbyAnswers = hobbyAnswers;
    }

    public byte getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(byte personalityType) {
        this.personalityType = personalityType;
    }

    public byte getLikesAnimals() {
        return likesAnimals;
    }

    public void setLikesAnimals(byte likesAnimals) {
        this.likesAnimals = likesAnimals;
    }

    public byte getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(byte genderPreference) {
        this.genderPreference = genderPreference;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
}
