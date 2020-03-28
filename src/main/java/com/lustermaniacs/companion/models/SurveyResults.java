package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

//enum Sports {
//    @JsonProperty("basketball")
//    BASKETBALL(0),
//    @JsonProperty("baseball")
//    BASEBALL(1),
//    @JsonProperty("football")
//    FOOTBALL(2),
//    @JsonProperty("soccer")
//    SOCCER(3),
//    @JsonProperty("hockey")
//    HOCKEY(4),
//    @JsonProperty("tennis")
//    TENNIS(5),
//    @JsonProperty("volleyball")
//    VOLLEYBALL(6);
//    int value;
//
//    Sports(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//}
//
//enum Food {
//    @JsonProperty("halal")
//    HALAL(0),
//    @JsonProperty("kosher")
//    KOSHER(1),
//    @JsonProperty("pizza")
//    PIZZA(2),
//    @JsonProperty("chinese")
//    CHINESE(3),
//    @JsonProperty("caribbean")
//    CARIBBEAN(4),
//    @JsonProperty("mexican")
//    MEXICAN(5),
//    @JsonProperty("italian")
//    ITALIAN(6),
//    @JsonProperty("sushi")
//    SUSHI(7),
//    @JsonProperty("indian")
//    INDIAN(8),
//    @JsonProperty("fast_food")
//    FAST_FOOD(9);
//    int value;
//
//    Food(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//}
//
//enum Music {
//    @JsonProperty("halal")
//    ROCK(0),
//    @JsonProperty("kosher")
//    POP(1),
//    @JsonProperty("pizza")
//    COUNTRY(2),
//    @JsonProperty("chinese")
//    RandB(3),
//    @JsonProperty("caribbean")
//    HIP_HOP(4),
//    @JsonProperty("mexican")
//    ELECTRONIC_DANCE(5),
//    @JsonProperty("italian")
//    JAZZ(6),
//    @JsonProperty("sushi")
//    BLUES(7),
//    @JsonProperty("indian")
//    CLASSICAL(8),
//    @JsonProperty("fast_food")
//    HEAVY_METAL(9);
//    int value;
//
//    Music(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//}

public class SurveyResults{
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

    public SurveyResults() {

    }

    public SurveyResults(Set<Integer> sport, Set<Integer> food, Set<Integer> music, Set<Integer> hobby, int personalityType, int likesAnimals, int genderPreference, int maxAge, int minAge, int maxDistance) {
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
