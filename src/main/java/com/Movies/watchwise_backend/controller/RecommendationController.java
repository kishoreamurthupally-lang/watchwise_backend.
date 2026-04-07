package com.Movies.watchwise_backend.controller;

import com.Movies.watchwise_backend.Repo.RecommendationRepository;
import com.Movies.watchwise_backend.model.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin("*")
public class RecommendationController {

    @Autowired
    RecommendationRepository repo;

    //  Save recommendation
    @PostMapping
    public Recommendation save(@RequestBody Recommendation rec) {
        return repo.save(rec);
    }

    //  Get all recommendations for a movie
    @GetMapping("/movie/{movieId}")
    public List<Recommendation> getByMovie(@PathVariable Long movieId) {
        return repo.findByMovieId(movieId);
    }

    //  Get all recommendations by user
    @GetMapping("/user/{email}")
    public List<Recommendation> getByUser(@PathVariable String email) {
        return repo.findByUserEmail(email);
    }

    //  Get all recommendations
    @GetMapping
    public List<Recommendation> getAll() {
        return repo.findAll();
    }
}