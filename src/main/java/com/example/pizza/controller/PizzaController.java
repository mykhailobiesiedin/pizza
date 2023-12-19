package com.example.pizza.controller;

import com.example.pizza.domain.entity.Pizza;
import com.example.pizza.exception.exceptions.CafeNotFoundException;
import com.example.pizza.service.interfaces.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaService service;

    @GetMapping("/all/{cafeName}/{cafeAddress}")
    public List<Pizza> getAllInCafe(@PathVariable String cafeName, @PathVariable String cafeAddress) {
        return service.getAllInCafe(cafeName, cafeAddress);
    }

    @GetMapping("/{cafeName}/{cafeAddress}/name/{name}")
    public Pizza getPizzaByName(@PathVariable String cafeName,
                                @PathVariable String cafeAddress, @PathVariable String name) {
        return service.getPizzaByName(cafeName, cafeAddress, name);
    }

    @GetMapping("/{cafeName}/{cafeAddress}/id/{id}")
    public Pizza getPizzaById(@PathVariable String cafeName,
                              @PathVariable String cafeAddress, @PathVariable long id){
        return service.getPizzaById(cafeName, cafeAddress, id);
    }

    @PostMapping("/create/{cafeName}/{cafeAddress}")
    public ResponseEntity<String> addPizza(@RequestBody Pizza pizza,
                                                   @PathVariable String cafeName, @PathVariable String cafeAddress) {
        try {
            service.addPizza(pizza, cafeName, cafeAddress);
            return new ResponseEntity<>("Pizza added successfully", HttpStatus.CREATED);
        } catch (CafeNotFoundException e) {
            return new ResponseEntity<>("Cafe not found", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/update/{cafeName}/{cafeAddress}")
    public ResponseEntity<String> updatePizza(@RequestBody Pizza pizza,
                                           @PathVariable String cafeName, @PathVariable String cafeAddress){
        try {
            service.updatePizza(pizza, cafeName, cafeAddress);
            return new ResponseEntity<>("Pizza updated successfully", HttpStatus.ACCEPTED);
        } catch (CafeNotFoundException e) {
            return new ResponseEntity<>("Cafe not found", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete-pizza-by-name/{cafeName}/{cafeAddress}/{name}")
    public void deleteByName(@PathVariable String cafeName,
                             @PathVariable String cafeAddress, @PathVariable String name) {
        service.deleteByName(cafeName, cafeAddress, name);
    }
}
