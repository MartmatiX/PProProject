package com.github.martmatix.pproproject.controllers;

import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private UserService userService;

    @GetMapping(path = "/admin")
    public String returnAdminPage(Model model) {
        List<UserEntity> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "/administrator/adminPage";
    }

    // TODO: extract this into method, so there is no duplicate code
    @PostMapping(path = "/admin/approve/{username}")
    public String approveUser(@PathVariable String username, Principal principal) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()) {
            return "redirect:/admin?status?userRetrieveFail";
        }
        UserEntity userEntity = user.get();
        userEntity.setEnabled(true);
        userService.saveUser(userEntity);
        return "redirect:/admin?status=approved";
    }

    @PostMapping(path = "/admin/revoke/{username}")
    public String revokeUser(@PathVariable String username, Principal principal) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()) {
            return "redirect:/admin?status?userRetrieveFail";
        }
        UserEntity userEntity = user.get();
        userEntity.setEnabled(false);
        userService.saveUser(userEntity);
        return "redirect:/admin?status=revoked";
    }

    private String validateByUsername(String username, Principal principal) {
        if (username.trim().isBlank() || username.trim().isEmpty()) {
            return "redirect:/admin?status=idNotValid";
        }
        if (username.equals(principal.getName())) {
            return "redirect:/admin?status=alterSelfError";
        }
        return null;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
