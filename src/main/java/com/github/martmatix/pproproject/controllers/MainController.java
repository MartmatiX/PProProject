package com.github.martmatix.pproproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {

    @GetMapping
    public String returnWelcomePage() {
        return "/authenticated/welcomePage";
    }

}
