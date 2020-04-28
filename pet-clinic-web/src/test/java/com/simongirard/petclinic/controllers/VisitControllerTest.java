package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.model.Visit;
import com.simongirard.petclinic.services.PetService;
import com.simongirard.petclinic.services.VisitService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private VisitService visitService;

    @InjectMocks
    private VisitController visitController;

    private MockMvc mockMvc;

    private Visit visit;

    @BeforeEach
    void setUp() throws NotFoundException {
        visit = Visit.builder().id(1L).build();
        Owner owner = Owner.builder().id(1L).lastName("Girard").firstName("Simon").build();
        PetType petType = PetType.builder().id(1L).name("Dog").build();

        when(petService.findById(anyLong())).thenReturn(Pet.builder()
                                .id(1L)
                                .birthDay(LocalDate.of(2018,11,11))
                                .name("Clifford")
                                .visits(new HashSet<>())
                                .owner(owner)
                                .petType(petType).build());

        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }

    @Test
    void processNewvisitForm() throws Exception {
        when(visitService.save(any())).thenReturn(visit);

        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("date", "2018-11-11")
                            .param("description", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"))
                .andExpect(model().attributeExists("visit"));

        verify(visitService).save(any());
    }

    @Test
    void processNewvisitFormFail() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2018-11-11")
                .param("description", "    "))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"));

        verifyNoInteractions(visitService);
    }


}