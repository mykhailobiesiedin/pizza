package com.example.pizza.service.implementation;

import com.example.pizza.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements UserDetailsService {
    /**
     * Custom implementation of the Spring Security UserDetailsService.
     * This class is responsible for loading a user's details by username from the underlying
     * {@code EmployeeRepository}. It is typically used in the authentication process by Spring Security.
     *
     * @param repository The {@code EmployeeRepository} used to retrieve user details.
     * @return An implementation of the {@code UserDetails} interface containing user details.
     * @throws UsernameNotFoundException If no user is found with the specified username.
     */
    @Autowired
    private EmployeeRepository repository;

    /**
     * Load user details by username.
     * This method retrieves user details from the underlying {@code EmployeeRepository} based on
     * the provided username. If no user is found with the specified username, a
     * {@code UsernameNotFoundException} is thrown.
     *
     * @param username The username of the user whose details are to be loaded.
     * @return An implementation of the {@code UserDetails} interface containing user details.
     * @throws UsernameNotFoundException If no user is found with the specified username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return user;
    }
}
