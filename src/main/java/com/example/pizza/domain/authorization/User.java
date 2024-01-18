package com.example.pizza.domain.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


/**
 * The User class represents a user entity with details such as ID, username, password, and associated roles.
 * It implements the UserDetails interface, making it suitable for use in Spring Security.
 * It is annotated as an entity to be mapped to a database table named "user".
 */
@Entity
@Table(name = "user")
public class User implements UserDetails {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * The password of the user.
     */
    @Column(name = "password")
    private String password;

    /**
     * The username of the user.
     */
    @Column(name = "username")
    private String username;

    /**
     * The set of roles associated with this user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Default constructor for the User class.
     */
    public User() {
    }

    /**
     * Get the unique identifier for the user.
     *
     * @return The unique identifier for the user.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the unique identifier for the user.
     *
     * @param id The unique identifier for the user.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the set of roles associated with this user.
     *
     * @return The set of roles associated with this user.
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Check if the user account is non-expired.
     *
     * @return True if the user account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the user account is non-locked.
     *
     * @return True if the user account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the user credentials are non-expired.
     *
     * @return True if the user credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user account is enabled.
     *
     * @return True if the user account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
