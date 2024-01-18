package com.example.pizza.repository;

import com.example.pizza.domain.authorization.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * The RoleRepository interface is a Spring Data JPA repository for performing database operations on the Role entity.
 * It extends the JpaRepository interface.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByNameIn(Set<String> roleNames);

    @Query("SELECT r.name FROM Role r JOIN r.users u WHERE u.username = :username")
    Set<String> findRolesByUsername(@Param("username") String username);
}
