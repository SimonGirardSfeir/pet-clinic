package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VetMapServiceTest {

    private VetMapService vetMapService;

    @Mock
    private SpecialityMapService specialityMapService;

    private Long vetID = 1L;

    private String lastName = "Dupond";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vetMapService = new VetMapService(specialityMapService);

        vetMapService.save(Vet.builder().id(vetID).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Vet> vets = vetMapService.findAll();

        assertEquals(1, vets.size());
    }

    @Test
    void findById() {
        Vet vet = vetMapService.findById(vetID);

        assertEquals(vetID, vet.getId());
    }

    @Test
    void save() {
        Vet vet = vetMapService.save(Vet.builder().id(2L).build());

        assertEquals(2L, vet.getId());

    }

    @Test
    void saveVetWithSpecialityWithNoId() {
        Vet vet = Vet.builder().build();
        Speciality speciality = Speciality.builder().build();

        Set<Speciality> specialities = new HashSet<>();
        specialities.add(speciality);
        vet.setSpecialities(specialities);

        when(specialityMapService.save(speciality)).thenReturn(Speciality.builder().id(1L).build());

        assertEquals(1L, vetMapService.save(vet).getSpecialities().iterator().next().getId());


    }

    @Test
    void delete() {
        vetMapService.delete(vetMapService.findById(vetID));

        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    void deleteById() {
        vetMapService.deleteById(vetID);

        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Vet vet = vetMapService.findByLastName(lastName);

        assertEquals(vetID, vet.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Vet vet = vetMapService.findByLastName("Toto");

        assertNull(vet);
    }
}