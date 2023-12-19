package com.example.pizza.controller;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.service.interfaces.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafe")
public class CafeController {

    @Autowired
    private CafeService service;

    //Works
    @GetMapping("/all")
    public List<Cafe> getAllCafes() {
        return service.getAllCafes();
    }

    @GetMapping("/chain/{name}")
    public List<Cafe> getCafeChain(@PathVariable String name) {
        return service.getCafeChain(name);
    }

    @GetMapping("/id/{id}")
    public Cafe getCafeById(@PathVariable long id){
        return service.getCafeById(id);
    }

    //Works
    @GetMapping("/name-address/{cafeName}/{address}")
    public Cafe getCafeByNameAndAddress(@PathVariable String cafeName, @PathVariable String address) {
        return service.getCafeByNameAndAddress(cafeName, address);
    }

    //works for creating
    @PostMapping("/create")
    public ResponseEntity<String> createCafe(@RequestBody Cafe cafe) {
        service.createCafe(cafe);
        return new ResponseEntity<>("Cafe added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCafe(@RequestBody Cafe cafe){
        service.updateCafe(cafe);
        return new ResponseEntity<>("Cafe updated successfully", HttpStatus.ACCEPTED);
    }

    //works
    @DeleteMapping("/delete-by-id/{cafeId}")
    public void deleteCafeById(@PathVariable long cafeId) {
        service.deleteCafeById(cafeId);
    }

    //works
    @DeleteMapping("/delete-chain/{name}")
    public void deleteCafeChain(@PathVariable String name) {
        service.deleteCafeChain(name);
    }

    @DeleteMapping("/delete-by-name-address/{name}/{address}")
    public void deleteCafeByNameAndAddress(@PathVariable String name, @PathVariable String address) {
        service.deleteCafeByNameAndAddress(name, address);
    }
}
