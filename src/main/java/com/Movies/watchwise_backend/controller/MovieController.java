package com.Movies.watchwise_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Movies.watchwise_backend.model.Movie;
import com.Movies.watchwise_backend.service.MovieService;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin("*")
public class MovieController {

    @Autowired
    MovieService service;

    @GetMapping
    public List<Movie> all() {
        return service.getAll();
    }

    @PostMapping
    public Movie save(@RequestBody Movie m) {
        return service.save(m);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Movie one(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/search")
    public List<Movie> search(@RequestParam String keyword) {
        return service.search(keyword);
        
        
    }

    
    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie m){
        m.setId(id);
        return service.save(m);
    }   
    @GetMapping("/genre/{g}")
    public List<Movie> genre(@PathVariable String g) {
        return service.genre(g);
    }
}