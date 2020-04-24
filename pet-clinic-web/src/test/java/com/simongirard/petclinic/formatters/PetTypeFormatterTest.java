package com.simongirard.petclinic.formatters;

import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTest {

    private PetTypeFormatter petTypeFormatter;

    private Set<PetType> petTypes;

    @Mock
    private PetTypeService petTypeService;

    @BeforeEach
    void setUp() {
        petTypeFormatter = new PetTypeFormatter(petTypeService);
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());
    }

    @Test
    void printTest() {
        PetType petType = PetType.builder().id(1L).name("Dog").build();

        String petTypeName = petTypeFormatter.print(petType, Locale.ENGLISH);

        assertEquals("Dog", petTypeName);
    }
    @Test
    void parseTest() throws ParseException {
        when(petTypeService.findAll()).thenReturn(petTypes);

        PetType petType = petTypeFormatter.parse("Cat", Locale.ENGLISH);

        assertEquals("Cat", petType.getName());
        assertEquals(2L, petType.getId());
    }

    @Test
    void parseFail() {
        assertThrows(ParseException.class, () -> petTypeFormatter.parse("Fake Animal", Locale.ENGLISH));
    }
}