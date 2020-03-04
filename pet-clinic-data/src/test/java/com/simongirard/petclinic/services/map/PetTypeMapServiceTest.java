package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeMapServiceTest {

    private PetTypeMapService petTypeMapService;

    private Long petTypeId = 1L;

    @BeforeEach
    void setUp() {
        petTypeMapService = new PetTypeMapService();
        petTypeMapService.save(PetType.builder().id(petTypeId).build());
    }

    @Test
    void findAll() {
        Set<PetType> petTypes = petTypeMapService.findAll();

        assertEquals(1, petTypes.size());
    }

    @Test
    void findById() {
        PetType petType = petTypeMapService.findById(petTypeId);

        assertEquals(petTypeId, petType.getId());
    }

    @Test
    void save() {

        PetType petType = petTypeMapService.save(PetType.builder().id(2L).build());

        assertEquals(2L, petType.getId());
    }

    @Test
    void delete() {
        petTypeMapService.delete(petTypeMapService.findById(petTypeId));

        assertEquals(0, petTypeMapService.findAll().size());
    }

    @Test
    void deleteById() {
        petTypeMapService.deleteById(petTypeId);

        assertEquals(0, petTypeMapService.findAll().size());
    }
}