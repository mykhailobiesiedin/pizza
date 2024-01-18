package com.example.pizza.repository;

import com.example.pizza.domain.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * The UserRepository interface is a Spring Data JPA repository for performing database operations on the User entity.
 * It extends the JpaRepository interface and includes additional methods for specific queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds UserDetails by the username.
     *
     * @param username The username of the user.
     * @return UserDetails associated with the specified username or null if not found.
     */
    UserDetails findByUsername(String username);
}
