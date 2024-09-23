package com.github.martmatix.pproproject.controllers;

import com.github.martmatix.pproproject.DTOs.RegistrationFormUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @GetMapping(path = "/register")
    public String returnRegistrationForm() {
        return "/all/registration/registrationPage";
    }

    @PostMapping(path = "/register/validateRegistration")
    public String validateRegistrationForm(@ModelAttribute("registrationUser")RegistrationFormUserDTO userDTO) {

        return "redirect:/register";
    }

}
