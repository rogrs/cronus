package br.com.rogrs.cronus.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
@Data
public class Planet {

    private String name;
    private String rotation_period;
    private String orbital_period;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surface_water;
    private String population;
    private ArrayList<Object> residents;
    private ArrayList<String> films;
    private Date created;
    private Date edited;
    private String url;
}
