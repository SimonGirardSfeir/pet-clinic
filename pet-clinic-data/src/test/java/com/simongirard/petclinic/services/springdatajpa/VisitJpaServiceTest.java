package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Visit;
import com.simongirard.petclinic.repositories.VisitRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitJpaServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitJpaService visitJpaService;

    private Long visitId = 1L;

    private Visit visit;

    @BeforeEach
    void setUp() {
        visit = Visit.builder().id(visitId).build();
    }

    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);

        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> visitsReturn = visitJpaService.findAll();

        assertEquals(1, visitsReturn.size());
        verify(visitRepository).findAll();
    }

    @Test
    void findById() throws NotFoundException {
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        Visit visitReturn = visitJpaService.findById(visitId);

        assertNotNull(visitReturn);
        assertEquals(visit, visitReturn);
        verify(visitRepository).findById(visitId);
    }

    @Test
    void findByIdNotFound() {
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> visitJpaService.findById(visitId));
    }

    @Test
    void save() {
        Visit visitToSave = Visit.builder().id(1L).build();

        when(visitRepository.save(any())).thenReturn(visit);

        Visit visitSaved = visitJpaService.save(visitToSave);

        assertNotNull(visitSaved);
        assertEquals(visit, visitSaved);
        verify(visitRepository).save(visitToSave);
    }

    @Test
    void delete() {
        visitJpaService.delete(visit);

        verify(visitRepository).delete(visit);
    }

    @Test
    void deleteById() {
        visitJpaService.deleteById(visitId);

        verify(visitRepository).deleteById(visitId);
    }
}