package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.PetService;
import com.simongirard.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    void saveOwnerWithPetWithoutPetType() {
        Pet pet = Pet.builder().id(1L).build();

        Set<Pet> pets = new HashSet<>();
        pets.add(pet);

        assertThrows(RuntimeException.class, () -> ownerMapService.save(Owner.builder().pets(pets).build()));
    }

    @Test
    void saveOwnerWithPetWithPetTypeWithNoId() {
        Pet pet = Pet.builder().id(1L).build();
        PetType petType = PetType.builder().build();
        pet.setPetType(petType);

        Set<Pet> pets = new HashSet<>();
        pets.add(pet);

        when(petTypeService.save(petType)).thenReturn(PetType.builder().id(1L).build());

        assertEquals(1L, ownerMapService.save(Owner.builder().pets(pets).build()).getPets().iterator().next().getPetType().getId());
    }

    @Test
    void saveOWnerWithPetWithNoId() {
        Pet pet = Pet.builder().build();
        PetType petType = PetType.builder().build();
        pet.setPetType(petType);

        Set<Pet> pets = new HashSet<>();
        pets.add(pet);

        when(petService.save(pet)).thenReturn(Pet.builder().id(1L).build());

        assertEquals(1L, ownerMapService.save(Owner.builder().pets(pets).build()).getPets().iterator().next().getId());
    }

    @Test
    void saveNullOwner() {
        assertNull(ownerMapService.save(null));
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

    @Test
    void findByLastNameLike() {
        List<Owner> owners = ownerMapService.findAllByLastNameLike(lastName);

        assertEquals(1, owners.size());
    }
}