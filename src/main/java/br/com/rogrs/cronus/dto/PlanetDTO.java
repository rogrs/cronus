package br.com.rogrs.cronus.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanetDTO {

    @JsonProperty("name")
    private String name;
    @JsonProperty("rotation_period")
    private String rotationPeriod;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    @JsonProperty("diameter")
    private String diameter;
    @JsonProperty("climate")
    private String climate;
    @JsonProperty("gravity")
    private String gravity;
    @JsonProperty("terrain")
    private String terrain;
    @JsonProperty("surface_water")
    private String surfaceWater;
    @JsonProperty("population")
    private String population;
    @JsonProperty("residents")
    private ArrayList<Object> residents;
    @JsonProperty("films")
    private ArrayList<String> films;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("edited")
    private Date edited;
    @JsonProperty("url")
    private String url;
}
