package com.Movies.watchwise_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;
    private String movieTitle;
    private String userEmail;
    private int rating;

    @Column(length = 1000)
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();
}





//To test your new Recommendation System in Postman, you need to make sure your Spring Boot application is running. Since you added new fields like movieTitle and userEmail to your model, your JSON body must match those exactly.
//
//Here is how to call each of your new endpoints in Postman:
//
//1. Save a New Recommendation (POST)
//This will trigger your save() method and store the data in the recommendations table.
//
//Method: POST
//
//URL: http://localhost:9191/api/recommendations
//
//Body Tab: Select raw and change the dropdown to JSON.
//
//JSON Content:
//
//JSON
//{
//    "movieId": 1,
//    "movieTitle": "The Housemaid",
//    "userEmail": "kishore@example.com",
//    "rating": 9,
//    "comment": "Incredible thriller! The plot twists kept me on the edge of my seat."
//}
//Expected Result: A 200 OK or 201 Created status with the saved object (including its new id and createdAt timestamp) returned in the response.
//
//2. Get Recommendations by Movie (GET)
//This tests your findByMovieId repository method.
//
//Method: GET
//
//URL: http://localhost:9191/api/recommendations/movie/1
//
//Expected Result: An array of all recommendations submitted for the movie with ID 1.
//
//3. Get Recommendations by User Email (GET)
//This tests your findByUserEmail repository method.
//
//Method: GET
//
//URL: http://localhost:9191/api/recommendations/user/kishore@example.com
//
//Expected Result: A list of every movie review submitted by that specific email address.
//
//4. Get All Recommendations (GET)
//Use this to see everything currently in your database.
//
//Method: GET
//
//URL: http://localhost:9191/api/recommendations
//
//Expected Result: A full list of all recommendations across all movies and users.