package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.repositories.VetRepository;
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
class VetJpaServiceTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetJpaService vetJpaService;

    private Long vetId = 1L;

    private String lastName = "Dupond";

    private Vet vet;
    private Set<Vet> vets;

    @BeforeEach
    void setUp() {
        vet = Vet.builder().id(vetId).lastName(lastName).build();
        vets = new HashSet<>();
        vets.add(vet);
    }

    @Test
    void findByLastName() {
        when(vetRepository.findByLastName(any())).thenReturn(vet);

        Vet vetReturn = vetJpaService.findByLastName(lastName);

        assertEquals(vetId, vetReturn.getId());
        assertEquals(lastName, vetReturn.getLastName());
        verify(vetRepository).findByLastName(eq(lastName));
    }

    @Test
    void findAll() {
        when(vetRepository.findAll()).thenReturn(vets);

        Set<Vet> vetsReturn = vetJpaService.findAll();

        assertEquals(1, vetsReturn.size());
        verify(vetRepository).findAll();
    }

    @Test
    void findById() throws NotFoundException {
        when(vetRepository.findById(anyLong())).thenReturn(Optional.of(vet));

        Vet vetReturn = vetJpaService.findById(vetId);

        assertNotNull(vetReturn);
        assertEquals(vet, vetReturn);
        verify(vetRepository).findById(eq(vetId));
    }

    @Test
    void findByIdNotFound() {
        when(vetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vetJpaService.findById(vetId));
    }

    @Test
    void save() {
        Vet vetToSave = Vet.builder().lastName(lastName).build();

        when(vetRepository.save(any())).thenReturn(vet);

        Vet savedVet = vetJpaService.save(vetToSave);

        assertNotNull(savedVet);
        assertEquals(vet, savedVet);
        verify(vetRepository).save(eq(vetToSave));
    }

    @Test
    void delete() {
        vetJpaService.delete(vet);

        verify(vetRepository).delete(eq(vet));
    }

    @Test
    void deleteById() {
        vetJpaService.deleteById(vetId);

        verify(vetRepository).deleteById(eq(vetId));
    }
}