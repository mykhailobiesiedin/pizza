package com.example.pizza.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String name;

    @Column(name = "price")
    @Min(5)
    @Max(50)
    private double price;

    @Column(name = "size")
    @NotBlank
    @Size(max = 10)
    private String size;

    @Column(name = "ingredients")
    @NotBlank
    @Size(max = 89)
    private String ingredients;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;


//    private Employee employee;

    public Pizza() {
    }

    public Pizza(Cafe cafe){
        this.cafe = cafe;
    }

    public Pizza(String name, double price, String size, String ingredients) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.ingredients = ingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}
