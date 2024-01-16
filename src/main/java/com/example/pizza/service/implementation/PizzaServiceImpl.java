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


/**
 * Implementation of the {@code PizzaService} interface providing pizza-related functionalities.
 * This service class interacts with the underlying {@code PizzaRepository} and {@code CafeRepository}
 * to perform operations related to pizzas and cafes.
 *
 * @see PizzaService
 * @see PizzaRepository
 * @see CafeRepository
 */
@Service
public class PizzaServiceImpl implements PizzaService {

    /**
     * Repository for managing pizza entities.
     */
    @Autowired
    private PizzaRepository pizzaRepository;

    /**
     * Repository for managing cafe entities.
     */
    @Autowired
    private CafeRepository cafeRepository;

    /**
     * Retrieves a list of pizzas available in a specific cafe.
     * This method fetches a list of pizzas from the {@code PizzaRepository} based on the provided
     * cafe name and address. If no pizzas are found for the specified cafe, an
     * {@code EmptyPizzaListException} is thrown.
     *
     * @param cafeName    The name of the cafe where pizzas are to be retrieved.
     * @param cafeAddress The address of the cafe where pizzas are to be retrieved.
     * @return A {@code List} containing pizzas available in the specified cafe.
     * @throws EmptyPizzaListException If no pizzas are found for the specified cafe.
     */
    @Override
    public List<Pizza> getAllInCafe(String cafeName, String cafeAddress) {
        List<Pizza> pizzaList = pizzaRepository.findByCafe_NameAndCafe_Address(cafeName, cafeAddress);
        if (pizzaList.isEmpty()) {
            // Check if the retrieved pizza list is empty
            throw new EmptyPizzaListException("The list of pizzas is empty");
        }
        return pizzaList;
    }

    /**
     * Retrieves a specific pizza from a cafe based on the provided pizza name and cafe details.
     * This method fetches a pizza from the {@code PizzaRepository} using the specified pizza name,
     * cafe name, and cafe address. If no pizza is found with the provided details, a
     * {@code PizzaNotFoundException} is thrown.
     *
     * @param name        The name of the pizza to be retrieved.
     * @param cafeName    The name of the cafe where the pizza is expected to be available.
     * @param cafeAddress The address of the cafe where the pizza is expected to be available.
     * @return The {@code Pizza} object corresponding to the provided name, cafe name, and cafe address.
     * @throws PizzaNotFoundException If no pizza is found with the specified name, cafe name, and cafe address.
     */
    @Override
    public Pizza getPizzaByName(String name, String cafeName, String cafeAddress) {
        Pizza pizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndName(name, cafeName, cafeAddress);
        if (pizza == null) {
            // Check if the retrieved pizza is null
            throw new PizzaNotFoundException("Pizza not found");
        }
        return pizza;
    }

    /**
     * Retrieves a specific pizza from a cafe based on the provided cafe details and pizza ID.
     * This method fetches a pizza from the {@code PizzaRepository} using the specified cafe name,
     * cafe address, and pizza ID. If no pizza is found with the provided details, a
     * {@code PizzaNotFoundException} is thrown.
     *
     * @param cafeName    The name of the cafe where the pizza is expected to be available.
     * @param cafeAddress The address of the cafe where the pizza is expected to be available.
     * @param id          The unique identifier of the pizza to be retrieved.
     * @return The {@code Pizza} object corresponding to the provided cafe details and pizza ID.
     * @throws PizzaNotFoundException If no pizza is found with the specified cafe details and pizza ID.
     */
    @Override
    public Pizza getPizzaById(String cafeName, String cafeAddress, long id) {
        Pizza pizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, id);
        if (pizza == null) {
            throw new PizzaNotFoundException("Pizza not found");
        }
        return pizza;
    }

    /**
     * Adds a new pizza to a cafe based on the provided cafe details.
     * This method fetches the cafe from the {@code CafeRepository} using the specified cafe name
     * and address. If the cafe is found, it associates the provided pizza with the cafe and saves
     * the pizza to the {@code PizzaRepository}. If the cafe is not found, a {@code CafeNotFoundException}
     * is thrown.
     *
     * @param pizza       The {@code Pizza} object to be added to the cafe.
     * @param cafeName    The name of the cafe to which the pizza is to be added.
     * @param cafeAddress The address of the cafe to which the pizza is to be added.
     * @throws CafeNotFoundException If no cafe is found with the specified name and address.
     */
    @Override
    public void addPizza(Pizza pizza, String cafeName, String cafeAddress) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);
        // Check if the retrieved cafe is not null

        if (cafe != null) {
            pizza.setCafe(cafe);
            pizzaRepository.save(pizza);
        } else {
            throw new CafeNotFoundException("Cafe not found");
        }
    }

    /**
     * Updates an existing pizza in a cafe based on the provided cafe details.
     * This method fetches the cafe from the {@code CafeRepository} using the specified cafe name
     * and address. If the cafe is found, it further checks for the existence of the pizza with the
     * provided pizza ID in the cafe. If the pizza is found, its details are updated with the information
     * from the provided pizza object, and the updated pizza is saved to the {@code PizzaRepository}.
     * If the cafe is not found, a {@code CafeNotFoundException} is thrown. If the pizza is not found in
     * the specified cafe, a {@code PizzaNotFoundException} is thrown.
     *
     * @param pizza       The {@code Pizza} object containing updated information.
     * @param cafeName    The name of the cafe where the pizza is expected to be available.
     * @param cafeAddress The address of the cafe where the pizza is expected to be available.
     * @throws CafeNotFoundException  If no cafe is found with the specified name and address.
     * @throws PizzaNotFoundException If no pizza is found with the specified ID in the specified cafe.
     */
    @Override
    public void updatePizza(Pizza pizza, String cafeName, String cafeAddress) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);

        // Check if the retrieved cafe is not null
        if (cafe != null) {
            Pizza existingPizza = pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizza.getId());

            // Check if the retrieved pizza is not null
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

    /**
     * Deletes pizzas with a specific name from a cafe based on the provided cafe details.
     * This method fetches the cafe from the {@code CafeRepository} using the specified cafe name
     * and address. If the cafe is found, it further deletes pizzas with the provided name from the
     * specified cafe using the {@code PizzaRepository}. If the cafe is not found, a
     * {@code CafeNotFoundException} is thrown.
     *
     * @param cafeName    The name of the cafe from which pizzas are to be deleted.
     * @param cafeAddress The address of the cafe from which pizzas are to be deleted.
     * @param name        The name of the pizzas to be deleted.
     * @throws CafeNotFoundException If no cafe is found with the specified name and address.
     */
    @Override
    public void deleteByName(String cafeName, String cafeAddress, String name) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress);
        if (cafe != null) {
            pizzaRepository.deletePizzaByNameAndCafe(name, cafe);
        } else {
            throw new CafeNotFoundException("Cafe not found");
        }
    }
}
