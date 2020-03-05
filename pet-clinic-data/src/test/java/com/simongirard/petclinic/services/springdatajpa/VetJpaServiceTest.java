package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.repositories.VetRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VetJpaServiceTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetJpaService vetJpaService;

    private Long vetId = 1L;

    private String lastName = "Dupond";

    private Vet vet;

    @BeforeEach
    void setUp() {
        vet = Vet.builder().id(vetId).lastName(lastName).build();
    }

    @Test
    void findByLastName() {
        when(vetRepository.findByLastName(any())).thenReturn(vet);

        Vet vetReturn = vetJpaService.findByLastName(lastName);

        assertEquals(vetId, vetReturn.getId());
        assertEquals(lastName, vetReturn.getLastName());
        verify(vetRepository).findByLastName(lastName);
    }

    @Test
    void findAll() {
        Set<Vet> vets = new HashSet<>();
        vets.add(vet);

        when(vetRepository.findAll()).thenReturn(vets);

        Set<Vet> vetsReturn = vetJpaService.findAll();

        assertEquals(1, vetsReturn.size());
        verify(vetRepository).findAll();
    }

    @Test
    void findById() {
        when(vetRepository.findById(anyLong())).thenReturn(Optional.of(vet));

        Vet vetReturn = vetJpaService.findById(vetId);

        assertNotNull(vetReturn);
        assertEquals(vet, vetReturn);
        verify(vetRepository).findById(vetId);
    }

    @Test
    void findByIdNotFound() {
        when(vetRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vet vetReturn = vetJpaService.findById(vetId);

        assertNull(vetReturn);
    }

    @Test
    void save() {
        Vet vetToSave = Vet.builder().id(1L).lastName(lastName).build();

        when(vetRepository.save(any())).thenReturn(vet);

        Vet savedVet = vetJpaService.save(vetToSave);

        assertNotNull(savedVet);
        assertEquals(vet, savedVet);
        verify(vetRepository).save(vetToSave);
    }

    @Test
    void delete() {
        vetJpaService.delete(vet);

        verify(vetRepository).delete(vet);
    }

    @Test
    void deleteById() {
        vetJpaService.deleteById(vetId);

        verify(vetRepository).deleteById(vetId);
    }
}