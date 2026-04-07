package com.Movies.watchwise_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "movies_new")  //  change table name
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private double rating;

    private String directorName;
    private int releaseYear;
    private String language;
    private int durationMinutes;

    private double imdbRating;
    private String leadActor;
    private String leadActress;

    private String description;
    private String awards;
    private String posterUrl;
    private String trailerUrl;
    
//    admin@watchwise.com Admin@123 Hardcoded in
}


