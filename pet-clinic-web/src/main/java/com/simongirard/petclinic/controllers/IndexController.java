package com.simongirard.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    public String index() {
        log.info("Home page");

        return "index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/*")
    public ModelAndView oups() {
        log.error("Wrong Url");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("message", "You type a wrong URL");

        return modelAndView;
    }
}
