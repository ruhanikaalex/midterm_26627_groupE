package auca.ac.rw.Online.quiz.management.repository;

import auca.ac.rw.Online.quiz.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    /**
     * Assessment Requirement: Implementation of existBy() method.
     * This method is used to check if a user exists with a given email.
     * Spring Data JPA automatically generates the query for this based on the method name.
     */
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}


