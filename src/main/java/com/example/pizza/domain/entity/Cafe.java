package com.example.pizza.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
@Table(name = "cafe")
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String name;

    @Column(name = "city")
    @NotBlank(message = "Field can not be empty")
    @Pattern(regexp = "[A-Z][a-z]{2,49}")
    private String city;


    @Column(name = "address", unique = true)
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$", message = "Invalid address format")
    @Size(max = 255, message = "Address is too long")
    private String address;


    @Column(name = "e-mail")
    @NotBlank
    @Pattern(regexp = "[\\w.]+@[a-zA-Z]+[\\w.]*", message = "Invalid email address format.")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "\\+\\d+", message = "Phone must start with '+' followed by digits")
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "cafe",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Pizza> pizzas;


//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;


    public Cafe() {
    }


    public Cafe(String name, String city, String email, String phone, String address) {
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

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
