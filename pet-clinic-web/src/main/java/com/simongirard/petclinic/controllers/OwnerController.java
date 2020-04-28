package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.services.OwnerService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String VIEWS_OWNER_VREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        log.info("Owner search page");

        model.addAttribute("owner", Owner.builder().build());

        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        if(owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if(results.isEmpty()) {
            log.info("No owner with name containing {}", owner.getLastName());

            result.rejectValue("lastName", "notFound", "not found");

            return "owners/findOwners";
        } else if(results.size() == 1) {
            log.info("Details page of owner with Id {}", owner.getId());

            owner = results.get(0);

            return "redirect:/owners/"+owner.getId();
        } else {
            log.info("All owner with name containing {}", owner.getLastName());

            model.addAttribute("selections", results);

            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable long ownerId) throws NotFoundException {
        log.info("Details page of owner with Id {}", ownerId);

        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));

        return modelAndView;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        log.info("Owner creation page");

        model.addAttribute("owner", Owner.builder().build());

        return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid @ModelAttribute Owner owner, BindingResult result) {
        if(result.hasErrors()) {
            result.getAllErrors().forEach( objectError -> log.debug(objectError.toString()));

            return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
        } else {
            Owner savedOwner = ownerService.save(owner);

            log.info("Owner saved. Owner with Id {}", savedOwner.getId());

            return "redirect:/owners/"+savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable String ownerId, Model model) throws NotFoundException {
        log.info("Owner edit page. Owner with Id {}", ownerId);

        model.addAttribute("owner", ownerService.findById(Long.parseLong(ownerId)));

        return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@PathVariable String ownerId, @Valid @ModelAttribute Owner owner,  BindingResult result) {
        if(result.hasErrors()) {
            result.getAllErrors().forEach( objectError -> log.debug(objectError.toString()));

            return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(Long.parseLong(ownerId));
            Owner savedOwner = ownerService.save(owner);

            log.info("Owner edited. Owner with Id {}", savedOwner.getId());

            return "redirect:/owners/"+savedOwner.getId();
        }
    }
}
