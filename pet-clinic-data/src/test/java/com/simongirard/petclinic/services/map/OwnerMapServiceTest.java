package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.services.PetService;
import com.simongirard.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;

    @Mock
    private PetService petService;

    @Mock
    private PetTypeService petTypeService;

    private final Long ownerId = 1L;

    private final String lastName = "Girard";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ownerMapService = new OwnerMapService(petTypeService, petService);

        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    void findById() {

        Owner owner = ownerMapService.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Owner owner2 = Owner.builder().id(2L).build();

        Owner savedOwner = ownerMapService.save(owner2);

        assertEquals(2L, savedOwner.getId());
    }


    @Test
    void saveENoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {

        Owner owner3 = ownerMapService.findByLastName(lastName);

        assertNotNull(owner3);
        assertEquals(ownerId, owner3.getId());
    }

    @Test
    void findByLastNameNotFound() {

        Owner owner4 = ownerMapService.findByLastName("Dupond");

        assertNull(owner4);
    }
}