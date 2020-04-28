package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.Visit;
import com.simongirard.petclinic.services.PetService;
import com.simongirard.petclinic.services.VisitService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService vetService, PetService petService) {
        this.visitService = vetService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable String petId, Model model) throws NotFoundException {
        log.info("Visit for Pet of id {}", petId);

        Pet pet = petService.findById(Long.parseLong(petId));
        model.addAttribute("pet", pet);
        Visit visit = Visit.builder().build();
        pet.getVisits().add(visit);
        visit.setPet(pet);

        return visit;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable String petId, Model model) {
        log.info("Visit creation page for pet of id {}", petId);

        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if(result.hasErrors()) {
            result.getAllErrors().forEach( objectError -> log.debug(objectError.toString()));

            return "pets/createOrUpdateVisitForm";
        } else {
            Visit savedVisit = visitService.save(visit);

            log.info("Visit saved. Visit with Id {}", savedVisit.getId());

            return "redirect:/owners/{ownerId}";
        }
    }

}
