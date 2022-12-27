package br.com.rogrs.cronus.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Film {

    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private String release_date;
    private ArrayList<String> characters;
    private ArrayList<String> planets;
    private ArrayList<String> starships;
    private ArrayList<String> vehicles;
    private ArrayList<String> species;
    private Date created;
    private Date edited;
    private String url;
}
