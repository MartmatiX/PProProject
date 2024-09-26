package com.github.martmatix.pproproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping(path = "/profile")
    public String returnProfile() {
        return "/authenticated/profilePage";
    }

}
