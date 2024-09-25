package com.github.martmatix.pproproject.controllers;

import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    @GetMapping(path = "/admin")
    public String returnAdminPage(Model model) {
        List<UserEntity> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "/administrator/adminPage";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
