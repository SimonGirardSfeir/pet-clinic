package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.services.OwnerService;
import javassist.NotFoundException;
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
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if(results.size() == 1) {
            owner = results.get(0);
            return "redirect:/owners/"+owner.getId();
        } else {
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable long ownerId) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));

        return modelAndView;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());

        return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid @ModelAttribute Owner owner, BindingResult result) {
        if(result.hasErrors()) {
            return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
        } else {
            Owner savedOwner = ownerService.save(owner);

            return "redirect:/owners/"+savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable String ownerId, Model model) throws NotFoundException {
        model.addAttribute("owner", ownerService.findById(Long.parseLong(ownerId)));
        return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid @ModelAttribute Owner owner, @PathVariable String ownerId,  BindingResult result) {
        if(result.hasErrors()) {
            return VIEWS_OWNER_VREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(Long.parseLong(ownerId));
            Owner savedOwner = ownerService.save(owner);

            return "redirect:/owners/"+savedOwner.getId();
        }
    }
}
