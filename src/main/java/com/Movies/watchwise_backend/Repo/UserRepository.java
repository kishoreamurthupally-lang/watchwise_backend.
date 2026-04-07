package com.Movies.watchwise_backend.Repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Movies.watchwise_backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //  case-insensitive email search
    Optional<User> findByEmailIgnoreCase(String email);
}