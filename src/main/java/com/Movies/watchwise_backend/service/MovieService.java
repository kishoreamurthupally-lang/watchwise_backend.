package com.Movies.watchwise_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Movies.watchwise_backend.Repo.MovieRepository;
import com.Movies.watchwise_backend.model.Movie;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repo;

    public List<Movie> getAll() {
        return repo.findAll();
        
    }

    public Movie save(Movie m) {
        return repo.save(m);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Movie get(Long id) {
        return repo.findById(id).orElse(null);
    }
    
    
    

    
    public List<Movie> search(String k) {
        return repo.findByTitleContainingIgnoreCase(k);
    }
    
    

    public List<Movie> genre(String g) {
        return repo.findByGenre(g);
    }
}