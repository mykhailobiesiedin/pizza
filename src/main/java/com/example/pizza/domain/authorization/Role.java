package com.example.pizza.domain.authorization;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;

/**
 * The Role class represents an authority (or role) entity with details such as ID and name.
 * It implements the GrantedAuthority interface, making it suitable for use in Spring Security.
 * It is annotated as an entity to be mapped to a database table named "authority".
 */
@Entity
@Table(name = "authority")
public class Role implements GrantedAuthority {

    /**
     * The unique identifier for the authority.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * The name of the authority (role).
     */
    @Column(name = "name")
    private String name;

    /**
     * The set of users associated with this authority.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    /**
     * Get the set of users associated with this authority.
     *
     * @return The set of users associated with this authority.
     */
    public Set<User> getEmployees() {
        return users;
    }

    /**
     * Set the set of users associated with this authority.
     *
     * @param users The set of users to be associated with this authority.
     */
    public void setEmployees(Set<User> users) {
        this.users = users;
    }

    /**
     * Default constructor for the Role class.
     */
    public Role() {
    }

    /**
     * Get the unique identifier for the authority.
     *
     * @return The unique identifier for the authority.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the unique identifier for the authority.
     *
     * @param id The unique identifier for the authority.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the name of the authority.
     *
     * @return The name of the authority.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the authority.
     *
     * @param name The name of the authority.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the authority name. Implemented from the GrantedAuthority interface.
     *
     * @return The authority name.
     */
    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
