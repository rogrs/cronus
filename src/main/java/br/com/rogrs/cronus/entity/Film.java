package br.com.rogrs.cronus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    private String title;
    @Column(name ="episode_id")
    @JsonProperty("episode_id")
    private int episodeId;
    @Column(name ="opening_crawl")
    @JsonProperty("opening_crawl")
    private String openingCrawl;
    private String director;
    private String producer;
    @Column(name ="release_date")
    @JsonProperty("release_date")
    private String releaseDate;

    private Date created;
    private Date edited;

    @Version
    private Long version;

}
