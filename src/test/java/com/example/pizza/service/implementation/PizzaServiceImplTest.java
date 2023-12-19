package com.example.pizza.service.implementation;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.domain.entity.Pizza;
import com.example.pizza.exception.exceptions.CafeNotFoundException;
import com.example.pizza.exception.exceptions.EmptyPizzaListException;
import com.example.pizza.exception.exceptions.PizzaNotFoundException;
import com.example.pizza.repository.CafeRepository;
import com.example.pizza.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaServiceImplTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private CafeRepository cafeRepository;
    @InjectMocks
    private PizzaServiceImpl pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllInCafe() {
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";
        Cafe cafe = new Cafe(cafeName, cafeAddress, "TestCity", "TestEmail", "TestPhone");

        List<Pizza> mockPizzaList = Arrays.asList(
                new Pizza("Margherita", 10.0, "Medium", "Tomato, Cheese"),
                new Pizza("Pepperoni", 12.0, "Large", "Pepperoni, Cheese")
        );

        when(pizzaRepository.findByCafe_NameAndCafe_Address(cafeName, cafeAddress)).thenReturn(mockPizzaList);
        List<Pizza> result = pizzaService.getAllInCafe(cafeName, cafeAddress);

        assertNotNull(result);
        assertEquals(mockPizzaList.size(), result.size());
        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_Address(cafeName, cafeAddress);
    }

    @Test
    void getAllInCafeEmptyList() {
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";

        when(pizzaRepository.findByCafe_NameAndCafe_Address(cafeName, cafeAddress)).thenReturn(List.of());
        assertThrows(EmptyPizzaListException.class, () -> pizzaService.getAllInCafe(cafeName, cafeAddress));
        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_Address(cafeName, cafeAddress);

    }

    @Test
    void getPizzaByName() {
        String pizzaName = "Margherita";
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";

        Pizza mockPizza = new Pizza(pizzaName, 10.0, "Medium", "Tomato, Cheese");
        when(pizzaRepository.findByCafe_NameAndCafe_AddressAndName(pizzaName, cafeName, cafeAddress)).thenReturn(mockPizza);
        Pizza pizza = pizzaService.getPizzaByName(pizzaName, cafeName, cafeAddress);

        assertNotNull(pizza);
        assertEquals(mockPizza, pizza);

        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_AddressAndName(pizzaName, cafeName, cafeAddress);

    }

    @Test
    void getPizzaByNameNotFound() {
        String pizzaName = "NotReal";
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";

        when(pizzaRepository.findByCafe_NameAndCafe_AddressAndName(pizzaName, cafeName, cafeAddress)).thenReturn(null);
        assertThrows(PizzaNotFoundException.class, () -> pizzaService.getPizzaByName(pizzaName, cafeName, cafeAddress));

        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_AddressAndName(pizzaName, cafeName, cafeAddress);
    }

    @Test
    void testGetPizzaById_Success() {
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";
        long pizzaId = 0;

        Pizza pizza = new Pizza("Margherita", 10.0, "Medium", "Tomato, Cheese");

        when(pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizzaId)).thenReturn(pizza);
        Pizza resultPizza = pizzaService.getPizzaById(cafeName, cafeAddress, pizzaId);
        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizzaId);

        assertNotNull(resultPizza);
        assertEquals(pizzaId, resultPizza.getId());
        assertEquals("Margherita", resultPizza.getName());
        assertEquals(10.0, resultPizza.getPrice());
        assertEquals("Medium", resultPizza.getSize());
        assertEquals("Tomato, Cheese", resultPizza.getIngredients());
    }

    @Test
    void testGetPizzaById_PizzaNotFound() {
        // Arrange
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";
        long nonExistentPizzaId = 2L;

        when(pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, nonExistentPizzaId)).thenReturn(null);
        PizzaNotFoundException exception = assertThrows(PizzaNotFoundException.class,
                () -> pizzaService.getPizzaById(cafeName, cafeAddress, nonExistentPizzaId));

        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, nonExistentPizzaId);
    }


    @Test
    void addPizza() {
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";

        Cafe cafe = new Cafe(cafeName, "TestCity", "TestEmail", "TestPhone", cafeAddress);
        Pizza pizza = new Pizza("Margherita", 10.0, "Medium", "Tomato, Cheese");

        when(cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress)).thenReturn(cafe);
        pizzaService.addPizza(pizza, cafeName, cafeAddress);
        verify(cafeRepository, times(1)).findCafeByNameAndAddress(cafeName, cafeAddress);
        verify(pizzaRepository, times(1)).save(pizza);
    }

    @Test
    void addPizzaCafeNotFound() {
        String cafeName = "NonExistingCafe";
        String cafeAddress = "NonExistingAddress";

        Pizza pizza = new Pizza("Margherita", 10.0, "Medium", "Tomato, Cheese");
        when(cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress)).thenReturn(null);

        CafeNotFoundException exception = assertThrows(CafeNotFoundException.class,
                () -> pizzaService.addPizza(pizza, cafeName, cafeAddress));

        verify(cafeRepository, times(1)).findCafeByNameAndAddress(cafeName, cafeAddress);

        verify(pizzaRepository, never()).save(any());

        assertEquals("Cafe not found", exception.getMessage());
    }

    @Test
    void updatePizza() {
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";

        Cafe cafe = new Cafe(cafeName, "TestCity", "TestEmail", "TestPhone", cafeAddress);
        Pizza existingPizza = new Pizza("Margherita", 10.0, "Medium", "Tomato, Cheese");
        Pizza updatedPizza = new Pizza("Pepperoni", 12.0, "Large", "Pepperoni, Cheese");


        when(cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress)).thenReturn(cafe);
        when(pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, updatedPizza.getId()))
                .thenReturn(existingPizza);

        pizzaService.updatePizza(updatedPizza, cafeName, cafeAddress);

        verify(cafeRepository, times(1)).findCafeByNameAndAddress(cafeName, cafeAddress);
        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, updatedPizza.getId());
        verify(pizzaRepository, times(1)).save(existingPizza);

        assertEquals("Pepperoni", existingPizza.getName());
        assertEquals(12.0, existingPizza.getPrice());
        assertEquals("Large", existingPizza.getSize());
        assertEquals("Pepperoni, Cheese", existingPizza.getIngredients());
        assertEquals(cafe, existingPizza.getCafe());
    }

    @Test
    void testUpdatePizza_PizzaNotFound() {
        // Arrange
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";
        Pizza pizza = new Pizza("Pepperoni", 12.0, "Large", "Pepperoni, Cheese");

        when(cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress)).thenReturn(new Cafe());
        when(pizzaRepository.findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizza.getId()))
                .thenReturn(null);

        // Act & Assert
        PizzaNotFoundException exception = assertThrows(PizzaNotFoundException.class,
                () -> pizzaService.updatePizza(pizza, cafeName, cafeAddress));

        // Verify
        verify(cafeRepository, times(1)).findCafeByNameAndAddress(cafeName, cafeAddress);
        verify(pizzaRepository, times(1)).findByCafe_NameAndCafe_AddressAndId(cafeName, cafeAddress, pizza.getId());
        verify(pizzaRepository, never()).save(any());
    }

    @Test
    void deleteByName() {
        String cafeName = "TestCafe";
        String cafeAddress = "TestAddress";
        String pizzaName = "Margherita";

        Cafe cafe = new Cafe(cafeName, "TestCity", "TestEmail", "TestPhone", cafeAddress);

        when(cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress)).thenReturn(cafe);

        assertDoesNotThrow(() -> pizzaService.deleteByName(cafeName, cafeAddress, pizzaName));

        verify(cafeRepository, times(1)).findCafeByNameAndAddress(cafeName, cafeAddress);
        verify(pizzaRepository, times(1)).deletePizzaByNameAndCafe(pizzaName, cafe);
    }
}