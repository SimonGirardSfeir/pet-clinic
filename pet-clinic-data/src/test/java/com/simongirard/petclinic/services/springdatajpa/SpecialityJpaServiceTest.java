package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.repositories.SpecialityRepository;
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
class SpecialityJpaServiceTest {

    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private SpecialityJpaService specialityJpaService;

    private Long specialityId = 1L;

    private Speciality speciality;

    @BeforeEach
    void setUp() {
        speciality = Speciality.builder().id(specialityId).build();
    }

    @Test
    void findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialities.add(speciality);

        when(specialityRepository.findAll()).thenReturn(specialities);

        Set<Speciality> specialitiesReturn = specialityJpaService.findAll();

        assertEquals(1, specialitiesReturn.size());
        verify(specialityRepository).findAll();
    }

    @Test
    void findById() {
        when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(speciality));

        Speciality specialityReturn = specialityJpaService.findById(specialityId);

        assertNotNull(specialityReturn);
        assertEquals(speciality, specialityReturn);

        verify(specialityRepository).findById(specialityId);
    }

    @Test
    void findByIdNotFound() {
        when(specialityRepository.findById(anyLong())).thenReturn(Optional.empty());

        Speciality specialityReturn = specialityJpaService.findById(specialityId);

        assertNull(specialityReturn);
    }

    @Test
    void save() {
        Speciality specialityToSave = Speciality.builder().id(1L).build();

        when(specialityRepository.save(any())).thenReturn(speciality);

        Speciality specialitySaved = specialityJpaService.save(specialityToSave);

        assertNotNull(specialitySaved);
        assertEquals(speciality, specialitySaved);

        verify(specialityRepository).save(specialityToSave);
    }

    @Test
    void delete() {
        specialityJpaService.delete(speciality);

        verify(specialityRepository).delete(speciality);
    }

    @Test
    void deleteById() {
        specialityJpaService.deleteById(specialityId);

        verify(specialityRepository).deleteById(specialityId);
    }
}