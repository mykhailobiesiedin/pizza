package com.example.pizza.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;


/**
 * The Cafe class represents a cafe entity with details such as ID, name, city, address, email, phone, and pizzas.
 * It is annotated as an entity to be mapped to a database table named "cafe".
 */
@Entity
@Table(name = "cafe")
public class Cafe {

    /**
     * The unique identifier for the cafe.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * The name of the cafe. It should start with an uppercase letter and may contain
     * only letters and spaces. It is a required field.
     */
    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String name;

    /**
     * The city where the cafe is located. It should start with an uppercase letter,
     * have a length between 2 and 49 characters, and contain only lowercase letters.
     * It is a required field.
     */
    @Column(name = "city")
    @NotBlank(message = "Field can not be empty")
    @Pattern(regexp = "[A-Z][a-z]{2,49}")
    private String city;


    /**
     * The address of the cafe. It should start with an uppercase letter and may contain
     * only letters and spaces. It is a required field.
     */
    @Column(name = "address", unique = true)
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$", message = "Invalid address format")
    @Size(max = 255, message = "Address is too long")
    private String address;


    /**
     * The email address of the cafe. It should match a valid email format.
     * It is a required field.
     */
    @Column(name = "e-mail")
    @NotBlank
    @Pattern(regexp = "[\\w.]+@[a-zA-Z]+[\\w.]*", message = "Invalid email address format.")
    private String email;

    /**
     * The phone number of the cafe. It should start with '+' followed by digits.
     */
    @Column(name = "phone")
    @Pattern(regexp = "\\+\\d+", message = "Phone must start with '+' followed by digits")
    private String phone;

    /**
     * The list of pizzas offered by the cafe.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "cafe",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Pizza> pizzas;

    /**
     * Default constructor for the Cafe class.
     */
    public Cafe() {
    }


    /**
     * Parameterized constructor for the Cafe class.
     *
     * @param name    The name of the cafe.
     * @param city    The city where the cafe is located.
     * @param email   The email address of the cafe.
     * @param phone   The phone number of the cafe.
     * @param address The address of the cafe.
     */
    public Cafe(String name, String city, String email, String phone, String address) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getter and setter methods for each field...

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
