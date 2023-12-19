package com.example.pizza.service.implementation;

import com.example.pizza.domain.entity.Cafe;
import com.example.pizza.exception.exceptions.CafeNotFoundException;
import com.example.pizza.exception.exceptions.EmptyCafeListException;
import com.example.pizza.exception.exceptions.IdNotFoundException;
import com.example.pizza.repository.CafeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CafeServiceImplTest {

    @Mock
    private CafeRepository cafeRepository;

    @InjectMocks
    private CafeServiceImpl cafeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getAllCafes() {
        List<Cafe> mockCafes = Collections.singletonList(new Cafe());

        when(cafeRepository.findAll()).thenReturn(mockCafes);
        List<Cafe> result = cafeService.getAllCafes();

        assertEquals(mockCafes, result);
    }

    @Test
    void getAllCafesEmptyList() {
        when(cafeRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(EmptyCafeListException.class, () -> cafeService.getAllCafes());
    }

    @Test
    void getCafeByNameAndAddress() {
        String cafeName = "TestName";
        String cafeAddress = "TestAddress";
        Cafe mockCafe = new Cafe();

        when(cafeRepository.findCafeByNameAndAddress(cafeName, cafeAddress)).thenReturn(mockCafe);
        Cafe cafe = cafeService.getCafeByNameAndAddress(cafeName, cafeAddress);
        assertEquals(mockCafe, cafe);
    }

    @Test
    void getCafeById() {
        long cafeId = 1000;
        Cafe mockCafe = new Cafe();

        when(cafeRepository.findById(cafeId)).thenReturn(Optional.of(mockCafe));
        Cafe cafe = cafeService.getCafeById(cafeId);
        assertEquals(mockCafe, cafe);
    }

    @Test
    void getCafeByIdNotFound() {
        long notExistingId = 122334;

        when(cafeRepository.findById(notExistingId)).thenReturn(Optional.empty());
        assertThrows(IdNotFoundException.class, () -> cafeService.getCafeById(notExistingId));
    }

    @Test
    void getCafeChain() {
        String cafeName = "TestCafe";
        List<Cafe> mockCafeList = Arrays.asList(
                new Cafe(cafeName, "TestCity1", "TestEmail1", "TestPhone1", "TestAddress1"),
                new Cafe(cafeName, "TestCity2", "TestEmail2", "TestPhone2", "Address2")
        );
        when(cafeRepository.findAll()).thenReturn(mockCafeList);
        List<Cafe> cafeList = cafeService.getCafeChain(cafeName);
        assertEquals(mockCafeList, cafeList);
    }

    @Test
    void getCafeChainEmptyList() {
        String cafeName = "TestCafe";
        when(cafeRepository.findAll()).thenReturn(List.of());

        assertThrows(EmptyCafeListException.class, () -> cafeService.getCafeChain(cafeName));
    }

    @Test
    void createCafe() {
        Cafe mockCafe = new Cafe("TestCafe", "TestCity", "TestEmail", "TestPhone", "TestAddress");
        when(cafeRepository.save(any(Cafe.class))).thenReturn(mockCafe);
        Cafe result = cafeService.createCafe(mockCafe);

        assertNotNull(result);
        assertEquals(mockCafe.getName(), result.getName());
        assertEquals(mockCafe.getAddress(), result.getAddress());
        assertEquals(mockCafe.getCity(), result.getCity());
        assertEquals(mockCafe.getEmail(), result.getEmail());
        assertEquals(mockCafe.getPhone(), result.getPhone());

        verify(cafeRepository, times(1)).save(mockCafe);
    }

    @Test
    void updateCafe() {
        Cafe existingCafe = new Cafe("ExistingCafe", "ExistingCity", "ExistingEmail", "ExistingPhone", "ExistingAddress");
        Cafe updatedCafe = new Cafe("UpdatedCafe", "UpdatedCity", "UpdatedEmail", "UpdatedPhone", "UpdatedAddress");

        when(cafeRepository.findById(existingCafe.getId())).thenReturn(Optional.of(existingCafe));
        when(cafeRepository.save(any(Cafe.class))).thenReturn(updatedCafe);
        Cafe result = cafeService.updateCafe(updatedCafe);

        assertNotNull(result);
        assertEquals(updatedCafe.getName(), result.getName());
        assertEquals(updatedCafe.getAddress(), result.getAddress());
        assertEquals(updatedCafe.getCity(), result.getCity());
        assertEquals(updatedCafe.getEmail(), result.getEmail());
        assertEquals(updatedCafe.getPhone(), result.getPhone());

        verify(cafeRepository, times(1)).findById(updatedCafe.getId());
        verify(cafeRepository, times(1)).save(existingCafe);
    }

    @Test
    void updateCafeNotFound() {
        Cafe nonExistingCafe = new Cafe("NonExistingCafe", "NonExistingAddress", "NonExistingCity", "NonExistingEmail", "NonExistingPhone");

        when(cafeRepository.findById(nonExistingCafe.getId())).thenReturn(Optional.empty());
        assertThrows(CafeNotFoundException.class, () -> cafeService.updateCafe(nonExistingCafe));
        verify(cafeRepository, times(1)).findById(nonExistingCafe.getId());
        verify(cafeRepository, never()).save(any(Cafe.class));
    }

    @Test
    void deleteCafeById() {
        long cafeId = 1;
        cafeService.deleteCafeById(cafeId);

        verify(cafeRepository, times(1)).deleteById(cafeId);
    }

    @Test
    void deleteCafeChain() {
        String cafeName = "Cafe name";
        cafeService.deleteCafeChain(cafeName);
        verify(cafeRepository, times(1)).deleteCafesByName(cafeName);
    }

    @Test
    void deleteCafeByNameAndAddress() {
        String cafeName = "Cafe name";
        String cafeAddress = "Cafe address";
        cafeService.deleteCafeByNameAndAddress(cafeName, cafeAddress);
        verify(cafeRepository, times(1)).deleteCafeByNameAndAddress(cafeName, cafeAddress);
    }
}