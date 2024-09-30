package com.github.martmatix.pproproject.controllers.authenticated;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller("/")
public class MainController {

    @GetMapping
    public String returnWelcomePage(Principal principal, Model model) {
        model.addAttribute("principalName", principal.getName());
        return "/authenticated/welcomePage";
    }

}
