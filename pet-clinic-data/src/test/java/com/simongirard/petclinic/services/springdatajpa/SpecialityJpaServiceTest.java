package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.repositories.SpecialityRepository;
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
class SpecialityJpaServiceTest {

    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private SpecialityJpaService specialityJpaService;

    private Long specialityId = 1L;

    private Speciality speciality;
    private Set<Speciality> specialities;

    @BeforeEach
    void setUp() {
        speciality = Speciality.builder().id(specialityId).build();
        specialities = new HashSet<>();
        specialities.add(speciality);
    }

    @Test
    void findAll() {
        when(specialityRepository.findAll()).thenReturn(specialities);

        Set<Speciality> specialitiesReturn = specialityJpaService.findAll();

        assertEquals(1, specialitiesReturn.size());
        verify(specialityRepository).findAll();
    }

    @Test
    void findById() throws NotFoundException {
        when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(speciality));

        Speciality specialityReturn = specialityJpaService.findById(specialityId);

        assertNotNull(specialityReturn);
        assertEquals(speciality, specialityReturn);

        verify(specialityRepository).findById(eq(specialityId));
    }

    @Test
    void findByIdNotFound() {
        when(specialityRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialityJpaService.findById(eq(specialityId)));
    }

    @Test
    void save() {
        Speciality specialityToSave = Speciality.builder().build();

        when(specialityRepository.save(any())).thenReturn(speciality);

        Speciality specialitySaved = specialityJpaService.save(specialityToSave);

        assertNotNull(specialitySaved);
        assertEquals(speciality, specialitySaved);

        verify(specialityRepository).save(eq(specialityToSave));
    }

    @Test
    void delete() {
        specialityJpaService.delete(speciality);

        verify(specialityRepository).delete(eq(speciality));
    }

    @Test
    void deleteById() {
        specialityJpaService.deleteById(specialityId);

        verify(specialityRepository).deleteById(eq(specialityId));
    }
}