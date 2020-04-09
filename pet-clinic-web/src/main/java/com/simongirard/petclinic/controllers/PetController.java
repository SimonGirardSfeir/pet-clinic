package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.OwnerService;
import com.simongirard.petclinic.services.PetService;
import com.simongirard.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static String VIEWWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
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

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        model.addAttribute("owner", owner);
        Pet pet = Pet.builder().build();
        pet.setOwner(owner);
        owner.getPets().add(pet);
        model.addAttribute("pet", pet);
        return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid @ModelAttribute Pet pet, BindingResult result, Model model) {
        if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        if(result.hasErrors()){
            model.addAttribute("pet", pet);
            return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable String petId, Model model) {
        model.addAttribute("pet", petService.findById(Long.parseLong(petId)));
        return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(Owner owner, @Valid @ModelAttribute Pet pet, BindingResult result, Model model) {
        if(result.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getPets().add(pet);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }


}
