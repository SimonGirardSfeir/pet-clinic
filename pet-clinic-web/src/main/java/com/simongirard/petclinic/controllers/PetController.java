package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.OwnerService;
import com.simongirard.petclinic.services.PetService;
import com.simongirard.petclinic.services.PetTypeService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        log.info("Pet type populated");

        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable String ownerId) throws NotFoundException {
        log.info("Owner found with Id {}", ownerId);

        return ownerService.findById(Long.parseLong(ownerId));
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        log.info("Pet creation page");

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
            log.debug("Pet already exists");

            result.rejectValue("name", "duplicate", "already exists");
        }

        owner.getPets().add(pet);
        pet.setOwner(owner);

        if(result.hasErrors()){
            result.getAllErrors().forEach( objectError -> log.debug(objectError.toString()));

            model.addAttribute("pet", pet);

            return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            Pet savedPet = petService.save(pet);

            log.info("Pet saved. Pet with Id {}", savedPet.getId());

            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable String petId, Model model) throws NotFoundException {
        model.addAttribute("pet", petService.findById(Long.parseLong(petId)));

        log.info("Pet edit page. Pet with Id {}", petId);

        return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(Owner owner, @Valid @ModelAttribute Pet pet, BindingResult result, Model model) {
        if(result.hasErrors()){
            result.getAllErrors().forEach( objectError -> log.debug(objectError.toString()));

            pet.setOwner(owner);
            model.addAttribute("pet", pet);

            return VIEWWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            pet.setOwner(owner);
            Pet savedPet = petService.save(pet);

            log.info("Pet saved. Pet with Id {}", savedPet.getId());

            return "redirect:/owners/" + owner.getId();
        }
    }


}
