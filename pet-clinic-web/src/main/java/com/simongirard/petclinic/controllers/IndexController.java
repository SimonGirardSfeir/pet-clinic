package com.simongirard.petclinic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    public String index() {
        return "index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/*")
    public ModelAndView oups() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("message", "404 Not Found");
        return modelAndView;
    }
}
