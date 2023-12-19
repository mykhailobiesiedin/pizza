package com.example.pizza.repository;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.domain.entity.Pizza;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findByCafe_NameAndCafe_Address(String cafeName, String cafeAddress);

    Pizza findByCafe_NameAndCafe_AddressAndName(String pizzaName, String cafeName, String cafeAddress);

    Pizza findByCafe_NameAndCafe_AddressAndId(String cafeName, String cafeAddress, long pizzaId);

    @Transactional
    void deletePizzaByNameAndCafe(String name, Cafe cafe);
}

