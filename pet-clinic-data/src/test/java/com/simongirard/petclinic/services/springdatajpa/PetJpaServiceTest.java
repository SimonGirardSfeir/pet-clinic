package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.repositories.PetRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetJpaServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetJpaService petJpaService;

    private Long petId = 1L;

    private Pet pet;
    private Set<Pet> pets;

    @BeforeEach
    void setUp() {
        pet = Pet.builder().id(petId).build();
        pets = new HashSet<>();
        pets.add(pet);
    }

    @Test
    void findAll() {
        when(petRepository.findAll()).thenReturn(pets);

        Set<Pet> returnPets = petJpaService.findAll();

        assertEquals(1, returnPets.size());

        verify(petRepository).findAll();
    }

    @Test
    void findById() throws NotFoundException {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));

        Pet petFound = petJpaService.findById(petId);

        assertNotNull(petFound);
        assertEquals(petId, petFound.getId());

        verify(petRepository).findById(eq(petId));
    }

    @Test
    void findByIdNotFound() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petJpaService.findById(petId));
    }

    @Test
    void save() {
        Pet petToSave = Pet.builder().build();

        when(petRepository.save(any())).thenReturn(pet);

        Pet petSaved = petJpaService.save(petToSave);

        assertNotNull(petSaved);
        assertEquals(pet, petSaved);

        verify(petRepository).save(eq(petToSave));
    }

    @Test
    void delete() {
        petJpaService.delete(pet);

        verify(petRepository).delete(eq(pet));
    }

    @Test
    void deleteById() {
        petJpaService.deleteById(petId);

        verify(petRepository).deleteById(eq(petId));
    }
}