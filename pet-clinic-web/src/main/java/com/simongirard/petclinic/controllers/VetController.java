package com.simongirard.petclinic.controllers;

import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Slf4j
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"/vets", "/vets/index", "/vets/index.html", "/vets.html"})
    public String listVets(Model model) {
        log.info("Vets home page");

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getVetsJson() {
        log.info("Vets in Json data");

        return vetService.findAll();
    }
}
