package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class SurveyResultsDTO {
    //Instance Fields
    private Set<Integer> sport;
    private Set<Integer> food;
    private Set<Integer> music;
    private Set<Integer> hobby;
    private int personalityType;
    private int likesAnimals;
    private int genderPreference;
    private int maxAge;
    private int minAge;
    private int maxDistance;

    public SurveyResultsDTO() {

    }

    public SurveyResultsDTO(Set<Integer> sport, Set<Integer> food, Set<Integer> music, Set<Integer> hobby, int personalityType, int likesAnimals, int genderPreference, int maxAge, int minAge, int maxDistance) {
        this.sport = sport;
        this.food = food;
        this.music = music;
        this.hobby = hobby;
        this.personalityType = personalityType;
        this.likesAnimals = likesAnimals;
        this.genderPreference = genderPreference;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.maxDistance = maxDistance;
    }

    public Set<Integer> getSport() {
        return sport;
    }

    public void setSport(Set<Integer> sport) {
        this.sport = sport;
    }

    public Set<Integer> getFood() {
        return food;
    }

    public void setFood(Set<Integer> food) {
        this.food = food;
    }

    public Set<Integer> getMusic() {
        return music;
    }

    public void setMusic(Set<Integer> music) {
        this.music = music;
    }

    public Set<Integer> getHobby() {
        return hobby;
    }

    public void setHobby(Set<Integer> hobby) {
        this.hobby = hobby;
    }

    public int getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(int personalityType) {
        this.personalityType = personalityType;
    }

    public int getLikesAnimals() {
        return likesAnimals;
    }

    public void setLikesAnimals(int likesAnimals) {
        this.likesAnimals = likesAnimals;
    }

    public int getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(int genderPreference) {
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

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}
