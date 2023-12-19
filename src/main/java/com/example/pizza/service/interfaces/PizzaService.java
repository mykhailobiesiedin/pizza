package com.example.pizza.service.interfaces;

import com.example.pizza.domain.entity.Pizza;
import jakarta.validation.Valid;

import java.util.List;

public interface PizzaService {
    List<Pizza> getAllInCafe(String cafeName, String cafeAddress);

    Pizza getPizzaByName(String name, String cafeName, String cafeAddress);

    Pizza getPizzaById(String cafeName, String cafeAddress, long id);

    void addPizza(@Valid Pizza pizza, String cafeName, String cafeAddress);

    void updatePizza(@Valid Pizza pizza,String cafeName, String cafeAddress);

    void deleteByName(String cafeName, String cafeAddress, String name);
}
