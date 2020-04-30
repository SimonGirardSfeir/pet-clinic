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

    @Mock
    private PetTypeService petTypeService;

    private PetType petTypeDog;
    private Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        petTypeFormatter = new PetTypeFormatter(petTypeService);
        petTypeDog = PetType.builder().id(1L).name("Dog").build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());
    }

    @Test
    void printTest() {
        String petTypeName = petTypeFormatter.print(petTypeDog, Locale.ENGLISH);

        assertEquals("Dog", petTypeName);
    }
    @Test
    void parseTest() throws ParseException {
        when(petTypeService.findAll()).thenReturn(petTypes);

        PetType petTypeReturn = petTypeFormatter.parse("Cat", Locale.ENGLISH);

        assertEquals("Cat", petTypeReturn.getName());
        assertEquals(2L, petTypeReturn.getId());
    }

    @Test
    void parseFail() {
        assertThrows(ParseException.class, () -> petTypeFormatter.parse("Fake Animal", Locale.ENGLISH));
    }
}