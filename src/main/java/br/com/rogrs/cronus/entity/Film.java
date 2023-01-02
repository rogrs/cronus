package br.com.rogrs.cronus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Column(name ="title",nullable = false)
    @JsonProperty("title")
    private String title;
    @Column(name ="episode_id")
    @JsonProperty("episode_id")
    private int episodeId;
    @Column(name ="opening_crawl", length = 4000)
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

    public Film(long id) {
        this.setId(id);
    }
}
