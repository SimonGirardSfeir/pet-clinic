package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.OwnerService;
import com.simongirard.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static String VIEWWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateForm";
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Set<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable String ownerId) {
        return ownerService.findById(Long.parseLong(ownerId));
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
}
