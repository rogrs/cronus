package br.com.rogrs.cronus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDTO {
    @JsonProperty("title")
    private String title;
    @JsonProperty("episode_id")
    private int episodeId;
    @JsonProperty("opening_crawl")
    private String openingCrawl;
    @JsonProperty("director")
    private String director;
    @JsonProperty("producer")
    private String producer;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("characters")
    private ArrayList<String> characters;
    @JsonProperty("planets")
    private ArrayList<String> planets;
    @JsonProperty("starships")
    private ArrayList<String> starships;
    @JsonProperty("vehicles")
    private ArrayList<String> vehicles;
    @JsonProperty("species")
    private ArrayList<String> species;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("edited")
    private Date edited;
    private String url;
}
