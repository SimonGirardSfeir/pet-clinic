package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecialityMapServiceTest {

    private SpecialityMapService specialityMapService;

    private Long specialityId = 1L;

    @BeforeEach
    void setUp() {
        specialityMapService = new SpecialityMapService();
        specialityMapService.save(Speciality.builder().id(specialityId).build());
    }

    @Test
    void findAll() {
        Set<Speciality> specialities = specialityMapService.findAll();

        assertEquals(1, specialities.size());
    }

    @Test
    void findById() {
        Speciality speciality = specialityMapService.findById(specialityId);

        assertEquals(specialityId, speciality.getId());
    }

    @Test
    void save() {
        Speciality speciality = specialityMapService.save(Speciality.builder().build());

        assertEquals(2L, speciality.getId());
    }

    @Test
    void delete() {
        specialityMapService.delete(specialityMapService.findById(specialityId));

        assertEquals(0, specialityMapService.findAll().size());
    }

    @Test
    void deleteById() {
        specialityMapService.deleteById(specialityId);

        assertEquals(0, specialityMapService.findAll().size());
    }
}