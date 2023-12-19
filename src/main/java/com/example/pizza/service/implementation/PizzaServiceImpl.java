package com.example.pizza.service.implementation;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.domain.entity.Pizza;
import com.example.pizza.exception.exceptions.CafeNotFoundException;
import com.example.pizza.exception.exceptions.EmptyPizzaListException;
import com.example.pizza.exception.exceptions.PizzaNotFoundException;
import com.example.pizza.repository.CafeRepository;
import com.example.pizza.repository.PizzaRepository;
import com.example.pizza.service.interfaces.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Override
    public List<Pizza> getAllInCafe(String cafeName, String cafeAddress) {
        List<Pizza> pizzaList = pizzaRepository.findByCafe_NameAndCafe_Address(cafeName, cafeAddress);
        if (pizzaList.isEmpty()) {
            throw new EmptyPizzaListException("The list of pizzas is empty");
        }
        return pizzaList;
    }

    @Override
    public Pizza getPizzaByName(String name, String cafeName, String cafeAddress) {
        Pizza pizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndName(name, cafeName, cafeAddress);
        if (pizza == null) {
            throw new PizzaNotFoundException("Pizza not found");
        }
        return pizza;
    }

    @Override
    public Pizza getPizzaById(String cafeName, String cafeAddress, long id) {
        Pizza pizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, id);
        if (pizza == null) {
            throw new PizzaNotFoundException("Pizza not found");
        }
        return pizza;
    }

    @Override
    public void addPizza(Pizza pizza, String cafeName, String cafeAddress) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);
        if (cafe != null) {
            pizza.setCafe(cafe);
            pizzaRepository.save(pizza);
        } else {
            throw new CafeNotFoundException("Cafe not found");
        }
    }

    //    @Override
//    public void updatePizza(Pizza pizza, String cafeName, String cafeAddress) {
//        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);
//        if (cafe == null) {
//            throw new CafeNotFoundException("Cafe not found");
//        }
//        Pizza existingPizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizza.getId());
//        if (existingPizza != null) {
//            existingPizza.setName(pizza.getName());
//            existingPizza.setPrice(pizza.getPrice());
//            existingPizza.setSize(pizza.getSize());
//            existingPizza.setIngredients(pizza.getIngredients());
//            existingPizza.setCafe(cafe);
//            pizzaRepository.save(existingPizza);
//        } else {
//            throw new PizzaNotFoundException("Pizza not found");
//        }
//    }
    @Override
    public void updatePizza(Pizza pizza, String cafeName, String cafeAddress) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);
        if (cafe != null) {
            Pizza existingPizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizza.getId());
            if (existingPizza == null) {
                throw new PizzaNotFoundException("Pizza not found");
            }
            existingPizza.setName(pizza.getName());
            existingPizza.setPrice(pizza.getPrice());
            existingPizza.setSize(pizza.getSize());
            existingPizza.setIngredients(pizza.getIngredients());
            existingPizza.setCafe(cafe);

            pizzaRepository.save(existingPizza);
        } else {
            throw new CafeNotFoundException("Cafe not found");
        }
    }

    @Override
    public void deleteByName(String cafeName, String cafeAddress, String name) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);
        pizzaRepository.deletePizzaByNameAndCafe(name, cafe);
    }
}
