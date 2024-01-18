package com.example.pizza.service.implementation;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.exception.exceptions.CafeNotFoundException;
import com.example.pizza.exception.exceptions.EmptyCafeListException;
import com.example.pizza.exception.exceptions.IdNotFoundException;
import com.example.pizza.repository.CafeRepository;
import com.example.pizza.service.interfaces.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implementation of the {@code CafeService} interface providing cafe-related functionalities.
 * This service class interacts with the underlying {@code CafeRepository}
 * to perform operations related to cafes.
 *
 * @see CafeService
 * @see CafeRepository
 */
@Service
public class CafeServiceImpl implements CafeService {

    /**
     * Repository for managing cafe entities.
     */
    @Autowired
    private CafeRepository cafeRepository;

    /**
     * Retrieves a list of all cafes from the repository.
     * This method overrides the base method to fetch all cafes from the underlying
     * repository and ensures that the retrieved list is not empty. If the list is empty,
     * an {@code EmptyCafeListException} is thrown.
     *
     * @return A {@code List} containing all cafes retrieved from the repository.
     * @throws EmptyCafeListException If the list of cafes is empty in the repository.
     */
    @Override
    public List<Cafe> getAllCafes() {
        List<Cafe> cafeList = cafeRepository.findAll();
        if (cafeList.isEmpty()) {
            // Check if the retrieved list is empty

            throw new EmptyCafeListException("The list of cafes is empty");
        }
        return cafeList;
    }

    /**
     * This method overrides the base method to fetch a cafe from the underlying
     * repository using the specified name and address. If no cafe is found, a
     * {@code CafeNotFoundException} is thrown.
     *
     * @param name    The name of the cafe to retrieve.
     * @param address The address of the cafe to retrieve.
     * @return The {@code Cafe} object corresponding to the provided name and address.
     * @throws CafeNotFoundException If no cafe is found with the specified name and address.
     */
    @Override
    public Cafe getCafeByNameAndAddress(String name, String address) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(name, address);
        if (cafe == null) {
            // Check if the retrieved cafe is null
            throw new CafeNotFoundException("Cafe with the following name and address is not found");
        }
        return cafe;
    }

    /**
     * Retrieves a cafe from the repository based on the provided ID.
     * This method overrides the base method to fetch a cafe from the underlying
     * repository using the specified ID. If no cafe is found with the given ID,
     * an {@code IdNotFoundException} is thrown.
     *
     * @param id The unique identifier of the cafe to retrieve.
     * @return The {@code Cafe} object corresponding to the provided ID.
     * @throws IdNotFoundException If no cafe is found with the specified ID.
     */
    @Override
    public Cafe getCafeById(long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Cafe with the following ID does not exist"));
    }

    /**
     * Retrieves a list of cafes forming a chain based on the provided chain name.
     * This method overrides the base method to filter cafes from the underlying
     * repository that have the specified name, forming a chain. If no cafes are found
     * with the given chain name, an {@code EmptyCafeListException} is thrown.
     *
     * @param name The name of the cafe chain to retrieve.
     * @return A {@code List} containing cafes forming the chain with the provided name.
     * @throws EmptyCafeListException If no cafes are found with the specified chain name.
     */
    @Override
    public List<Cafe> getCafeChain(String name) {
        List<Cafe> cafeChainList = cafeRepository.findAll()
                .stream()
                .filter(x -> x.getName().equals(name))
                .toList();
        // Check if the retrieved cafe chain list is empty
        if (cafeChainList.isEmpty()) {
            throw new EmptyCafeListException("Can not find cafe chain with the following name");
        }
        return cafeChainList;
    }

    /**
     * Creates a new cafe by saving it to the repository.
     * This method overrides the base method to save the provided {@code Cafe} object
     * to the underlying repository, creating a new cafe entry.
     *
     * @param cafe The {@code Cafe} object to be created and saved.
     * @return The {@code Cafe} object that has been created and saved, including any
     * auto-generated identifiers or fields.
     */
    @Override
    public Cafe createCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    /**
     * Updates an existing cafe with the provided information and saves it to the repository.
     * This method overrides the base method to find the existing cafe in the repository
     * by its ID, update its information with the provided {@code Cafe} object, and then save
     * the updated cafe back to the repository. If no cafe is found with the given ID, an
     * {@code CafeNotFoundException} is thrown.
     *
     * @param cafe The {@code Cafe} object containing updated information.
     * @return The {@code Cafe} object that has been updated and saved to the repository.
     * @throws CafeNotFoundException If no cafe is found with the specified ID.
     */
    @Override
    public Cafe updateCafe(Cafe cafe) {
        return cafeRepository.findById(cafe.getId())
                .map(existingCafe -> {
                    existingCafe.setName(cafe.getName());
                    existingCafe.setCity(cafe.getCity());
                    existingCafe.setAddress(cafe.getAddress());
                    existingCafe.setEmail(cafe.getEmail());
                    existingCafe.setPhone(cafe.getPhone());
                    return cafeRepository.save(existingCafe);
                })
                .orElseThrow(() -> new CafeNotFoundException("Cafe not found"));
    }

    /**
     * Deletes a cafe from the repository based on the provided ID.
     * This method overrides the base method to delete a cafe from the underlying
     * repository using the specified ID.
     *
     * @param cafeId The unique identifier of the cafe to be deleted.
     * @throws EmptyResultDataAccessException If no cafe is found with the specified ID.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteCafeById(long cafeId) {
        try {
            cafeRepository.deleteById(cafeId);
        } catch (EmptyResultDataAccessException e) {
            // Catch the exception that may be thrown if no cafe is found with the specified ID
            throw new EmptyResultDataAccessException("Cafe not found with ID: " + cafeId, 0);
        }
    }

    /**
     * Deletes a chain of cafes from the repository based on the provided chain name.
     * This method overrides the base method to delete cafes forming a chain from the
     * underlying repository using the specified chain name.
     *
     * @param name The name of the cafe chain to be deleted.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteCafeChain(String name) {
        cafeRepository.deleteCafesByName(name);
    }

    /**
     * Deletes a cafe from the repository based on the provided name and address.
     * This method overrides the base method to delete a cafe from the underlying
     * repository using the specified name and address.
     *
     * @param name    The name of the cafe to be deleted.
     * @param address The address of the cafe to be deleted.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteCafeByNameAndAddress(String name, String address) {
        cafeRepository.deleteCafeByNameAndAddress(name, address);
    }
}
