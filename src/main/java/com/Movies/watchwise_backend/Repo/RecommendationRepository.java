package com.Movies.watchwise_backend.Repo;

import com.Movies.watchwise_backend.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByMovieId(Long movieId);
    List<Recommendation> findByUserEmail(String userEmail);
}

