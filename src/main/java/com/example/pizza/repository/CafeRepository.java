package com.example.pizza.repository;

import com.example.pizza.domain.entity.Cafe;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    @Transactional
    void deleteCafesByName(String name);
    Cafe findCafeByNameAndAddress(String name, String address);
    @Transactional
    void deleteCafeByNameAndAddress(String name, String address);
}
