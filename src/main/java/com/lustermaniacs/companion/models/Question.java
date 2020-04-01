package com.lustermaniacs.companion.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Question {
    @JsonProperty("sport")
    SPORT,
    @JsonProperty("food")
    FOOD,
    @JsonProperty("music")
    MUSIC,
    @JsonProperty("hobby")
    HOBBY,
    @JsonProperty("personality")
    PERSONALITY,
    @JsonProperty("animals")
    ANIMALS;
}
