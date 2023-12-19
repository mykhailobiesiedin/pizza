package com.example.pizza.service.interfaces;

import com.example.pizza.domain.entity.Cafe;
import jakarta.validation.Valid;

import java.util.List;

public interface CafeService {
    List<Cafe> getAllCafes();

    Cafe getCafeByNameAndAddress(String name, String address);

    Cafe getCafeById(long id);
    List<Cafe> getCafeChain(String name);

    Cafe createCafe(@Valid Cafe cafe);

    Cafe updateCafe(@Valid Cafe cafe);

    void deleteCafeById(long cafeId);

    void deleteCafeChain(String name);
    void deleteCafeByNameAndAddress(String name, String address);

}
