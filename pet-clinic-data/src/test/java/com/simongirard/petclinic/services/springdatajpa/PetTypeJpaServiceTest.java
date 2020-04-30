package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.repositories.PetTypeRepository;
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
class PetTypeJpaServiceTest {

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private PetTypeJpaService petTypeJpaService;

    private Long petTypeId = 1L;

    private PetType petType;
    private Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        petType = PetType.builder().id(petTypeId).build();
        petTypes = new HashSet<>();
        petTypes.add(petType);
    }

    @Test
    void findAll() {
        when(petTypeRepository.findAll()).thenReturn(petTypes);

        Set<PetType> petTypesReturn = petTypeJpaService.findAll();

        assertEquals(1, petTypesReturn.size());

        verify(petTypeRepository).findAll();
    }

    @Test
    void findById() throws NotFoundException {
        when(petTypeRepository.findById(anyLong())).thenReturn(Optional.of(petType));

        PetType petTypeFound = petTypeJpaService.findById(petTypeId);

        assertNotNull(petTypeFound);
        assertEquals(petTypeId, petTypeFound.getId());

        verify(petTypeRepository).findById(eq(petTypeId));
    }

    @Test
    void findByIdNotFound() {
        when(petTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petTypeJpaService.findById(petTypeId));
    }

    @Test
    void save() {
        PetType petTypeToSave = PetType.builder().build();

        when(petTypeRepository.save(any())).thenReturn(petType);

        PetType petTypeSaved = petTypeJpaService.save(petTypeToSave);

        assertNotNull(petTypeSaved);
        assertEquals(petType, petTypeSaved);

        verify(petTypeRepository).save(eq(petTypeToSave));
    }

    @Test
    void delete() {
        petTypeJpaService.delete(petType);

        verify(petTypeRepository).delete(eq(petType));
    }

    @Test
    void deleteById() {
        petTypeJpaService.deleteById(petTypeId);

        verify(petTypeRepository).deleteById(eq(petTypeId));
    }
}