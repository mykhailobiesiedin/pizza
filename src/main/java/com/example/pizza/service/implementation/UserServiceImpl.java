package com.example.pizza.service.implementation;

import com.example.pizza.domain.authorization.Role;
import com.example.pizza.domain.authorization.User;
import com.example.pizza.repository.RoleRepository;
import com.example.pizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {
    /**
     * Custom implementation of the Spring Security UserDetailsService.
     * This class is responsible for loading a user's details by username from the underlying
     * {@code UserRepository}. It is typically used in the authentication process by Spring Security.
     *
     * @param repository The {@code UserRepository} used to retrieve user details.
     * @return An implementation of the {@code UserDetails} interface containing user details.
     * @throws UsernameNotFoundException If no user is found with the specified username.
     */
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Load user details by username.
     * This method retrieves user details from the underlying {@code UserRepository} based on
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

    @PreAuthorize("hasRole('ADMIN')")
    public void createUser(String username, String password, Set<String> roleNames) {
        // Создание нового объекта пользователя
        User user = new User();

        // Установка имени пользователя на основе переданного параметра
        user.setUsername(username);

        // Установка зашифрованного пароля для пользователя
        user.setPassword(passwordEncoder.encode(password));

        // Получение множества ролей из репозитория на основе переданных имен ролей
        Set<Role> roles = roleRepository.findByNameIn(roleNames);

        // Проверка, что все роли были найдены в репозитории
        if (roles.size() < roleNames.size()) {
            throw new IllegalArgumentException("One or more roles are invalid");
        }

        // Установка полученных ролей для пользователя
        user.setRoles(roles);

        // Сохранение нового пользователя в репозитории
        repository.save(user);
    }
}
