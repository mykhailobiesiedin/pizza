package com.example.pizza.repository;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.domain.entity.Pizza;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The PizzaRepository interface is a Spring Data JPA repository for performing database operations on the Pizza entity.
 * It extends the JpaRepository interface and includes additional methods for specific queries.
 */
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    /**
     * Finds pizzas by the name of the cafe and the address of the cafe.
     *
     * @param cafeName    The name of the cafe.
     * @param cafeAddress The address of the cafe.
     * @return A list of pizzas matching the specified cafe name and address.
     */
    List<Pizza> findByCafe_NameAndCafe_Address(String cafeName, String cafeAddress);

    /**
     * Finds a pizza by its name, the name of the cafe, and the address of the cafe.
     *
     * @param pizzaName   The name of the pizza.
     * @param cafeName    The name of the cafe.
     * @param cafeAddress The address of the cafe.
     * @return The found pizza or null if not found.
     */
    Pizza findByCafe_NameAndCafe_AddressAndName(String pizzaName, String cafeName, String cafeAddress);

    /**
     * Finds a pizza by its ID, the name of the cafe, and the address of the cafe.
     *
     * @param cafeName    The name of the cafe.
     * @param cafeAddress The address of the cafe.
     * @param pizzaId     The ID of the pizza.
     * @return The found pizza or null if not found.
     */
    Pizza findByCafe_NameAndCafe_AddressAndId(String cafeName, String cafeAddress, long pizzaId);

    /**
     * Deletes a pizza by its name and associated cafe.
     *
     * @param name The name of the pizza to be deleted.
     * @param cafe The cafe associated with the pizza.
     */
    @Transactional
    void deletePizzaByNameAndCafe(String name, Cafe cafe);
}

