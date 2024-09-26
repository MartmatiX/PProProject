package com.github.martmatix.pproproject.controllers;

import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ProfileController {

    private UserService userService;

    @GetMapping(path = "/profile/{username}")
    public String returnProfile(@PathVariable String username, Model model) {
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()) {
            return "redirect:/?status=userFetchFail";
        }
        user.get().setPassword("");
        model.addAttribute("user", user.get());
        return "/authenticated/profilePage";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
