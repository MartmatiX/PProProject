package com.github.martmatix.pproproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping(path = "/admin")
    public String returnAdminPage() {
        return "/administrator/adminPage";
    }

}
