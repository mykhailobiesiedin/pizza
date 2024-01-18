package com.example.pizza.repository;

import com.example.pizza.domain.entity.Cafe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The CafeRepository interface is a Spring Data JPA repository for performing database operations on the Cafe entity.
 * It extends the JpaRepository interface and includes additional methods for specific queries.
 */
@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    /**
     * Deletes cafes by their name.
     *
     * @param name The name of the cafes to be deleted.
     */
    @Transactional
    void deleteCafesByName(String name);

    /**
     * Finds a cafe by its name and address.
     *
     * @param name    The name of the cafe.
     * @param address The address of the cafe.
     * @return The found cafe or null if not found.
     */
    Cafe findCafeByNameAndAddress(String name, String address);

    @Transactional
    void deleteCafeByNameAndAddress(String name, String address);
}
