package com.example.pizza.service.implementation;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.exception.exceptions.CafeNotFoundException;
import com.example.pizza.exception.exceptions.EmptyCafeListException;
import com.example.pizza.exception.exceptions.IdNotFoundException;
import com.example.pizza.repository.CafeRepository;
import com.example.pizza.service.interfaces.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CafeServiceImpl implements CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    @Override
    public List<Cafe> getAllCafes() {
        List<Cafe> cafeList = cafeRepository.findAll();
        if (cafeList.isEmpty()) {
            throw new EmptyCafeListException("The list of cafes is empty");
        }
        return cafeList;
    }

    @Override
    public Cafe getCafeByNameAndAddress(String name, String address) {
        Cafe cafe = cafeRepository.findCafeByNameAndAddress(name, address);
        if (cafe == null) {
            throw new CafeNotFoundException("Cafe with the following name and address is not found");
        }
        return cafe;
    }

    @Override
    public Cafe getCafeById(long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Cafe with the following ID does not exist"));
    }

    @Override
    public List<Cafe> getCafeChain(String name) {
        List<Cafe> cafeChainList = cafeRepository.findAll()
                .stream()
                .filter(x -> x.getName().equals(name))
                .toList();
        if (cafeChainList.isEmpty()) {
            throw new EmptyCafeListException("Can not find cafe chain with the following name");
        }
        return cafeChainList;
    }

    @Override
    public Cafe createCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

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

    @Override
    public void deleteCafeById(long cafeId) {
        cafeRepository.deleteById(cafeId);
    }

    @Override
    public void deleteCafeChain(String name) {
        cafeRepository.deleteCafesByName(name);
    }

    @Override
    public void deleteCafeByNameAndAddress(String name, String address) {
        cafeRepository.deleteCafeByNameAndAddress(name, address);
    }
}
