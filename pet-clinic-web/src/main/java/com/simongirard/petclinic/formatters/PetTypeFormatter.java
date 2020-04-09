package com.simongirard.petclinic.formatters;

import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String s, Locale locale) throws ParseException {
        Set<PetType> findPetTypes = petTypeService.findAll();
        for(PetType type : findPetTypes) {
            if(type.getName().equals(s)) {
                return type;
            }
        }
        throw new ParseException("Type not found: " + s, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
