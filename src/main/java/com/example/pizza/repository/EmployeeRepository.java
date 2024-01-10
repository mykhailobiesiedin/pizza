package com.example.pizza.repository;

import com.example.pizza.domain.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
}
