package com.example.pizza.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * The Pizza class represents a pizza entity with details such as ID, name, price, size, ingredients, and the associated cafe.
 * It is annotated as an entity to be mapped to a database table named "pizza".
 */
@Entity
@Table(name = "pizza")
public class Pizza {

    /**
     * The unique identifier for the pizza.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * The name of the pizza. It should start with an uppercase letter and may contain
     * only letters and spaces. It is a required field.
     */
    @Column(name = "name", unique = true)
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String name;

    /**
     * The price of the pizza. It should be between 5 and 50.
     */
    @Column(name = "price")
    @Min(5)
    @Max(50)
    private double price;

    /**
     * The size of the pizza. It is a required field and should have a maximum length of 10 characters.
     */
    @Column(name = "size")
    @NotBlank
    @Size(max = 10)
    private String size;

    /**
     * The ingredients of the pizza. It is a required field and should have a maximum length of 89 characters.
     */
    @Column(name = "ingredients")
    @NotBlank
    @Size(max = 89)
    private String ingredients;

    /**
     * The cafe associated with the pizza.
     */
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;


    /**
     * Default constructor for the Pizza class.
     */
    public Pizza() {
    }

    /**
     * Parameterized constructor for the Pizza class with a cafe parameter.
     *
     * @param cafe The cafe associated with the pizza.
     */
    public Pizza(Cafe cafe){
        this.cafe = cafe;
    }

    /**
     * Parameterized constructor for the Pizza class.
     *
     * @param name        The name of the pizza.
     * @param price       The price of the pizza.
     * @param size        The size of the pizza.
     * @param ingredients The ingredients of the pizza.
     */
    public Pizza(String name, double price, String size, String ingredients) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.ingredients = ingredients;
    }

    // Getter and setter methods for each field...

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
