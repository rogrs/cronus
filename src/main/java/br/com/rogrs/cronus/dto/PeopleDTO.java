package br.com.rogrs.cronus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class PeopleDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("height")
    private String height;
    @JsonProperty("mass")
    private String mass;
    @JsonProperty("hair_color")
    private String hairColor;
    @JsonProperty("skin_color")
    private String skinColor;
    @JsonProperty("eye_color")
    private String eyeColor;
    @JsonProperty("birth_year")
    private String birthYear;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("homeworld")
    private String homeworld;
    @JsonProperty("films")
    private ArrayList<String> films;
    @JsonProperty("species")
    private ArrayList<Object> species;
    @JsonProperty("vehicles")
    private ArrayList<String> vehicles;
    @JsonProperty("starships")
    private ArrayList<String> starships;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("edited")
    private Date edited;
    @JsonProperty("url")
    private String url;
}
