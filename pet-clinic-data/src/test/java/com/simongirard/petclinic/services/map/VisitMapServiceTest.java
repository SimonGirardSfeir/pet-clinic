package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitMapServiceTest {

    private VisitMapService visitMapService;

    private Long visitId = 1L;

    @BeforeEach
    void setUp() {
        visitMapService = new VisitMapService();
        Pet pet = Pet.builder().id(2L).build();
        Owner owner = Owner.builder().id(3L).build();
        pet.setOwner(owner);

        visitMapService.save(Visit.builder().id(visitId).pet(pet).build());
    }

    @Test
    void findAll() {
        Set<Visit> visits = visitMapService.findAll();

        assertEquals(1, visits.size());
    }

    @Test
    void findById() {
        Visit visit = visitMapService.findById(visitId);

        assertEquals(visitId, visit.getId());
    }

    @Test
    void save() {
        Pet pet = Pet.builder().id(4L).build();

        Owner owner = Owner.builder().id(5L).build();
        pet.setOwner(owner);
        Visit visit = visitMapService.save(Visit.builder().pet(pet).build());

        assertEquals(2L, visit.getId());
    }

    @Test
    void saveVisitWithNoPet() {
        assertThrows(RuntimeException.class, () -> visitMapService.save(Visit.builder().build()));
    }

    @Test
    void delete() {
        visitMapService.delete(visitMapService.findById(visitId));

        assertEquals(0, visitMapService.findAll().size());
    }

    @Test
    void deleteById() {
        visitMapService.deleteById(visitId);

        assertEquals(0, visitMapService.findAll().size());
    }
}