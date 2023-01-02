package br.com.rogrs.cronus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    private String name;
    @Column(name ="rotation_period")
    private String rotationPeriod;
    @Column(name ="orbital_period")
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    @Column(name ="surface_water")
    private String surfaceWater;
    private String population;

    private Date created;

    private Date edited;
    private String url;

    @Version
    private Long version;

}
