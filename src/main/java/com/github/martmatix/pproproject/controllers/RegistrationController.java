package com.github.martmatix.pproproject.controllers;

import com.github.martmatix.pproproject.DTOs.RegistrationFormUserDTO;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private UserService userService;

    @GetMapping(path = "/register")
    public String returnRegistrationForm() {
        return "/all/registrationPage";
    }

    @PostMapping(path = "/register/validateRegistration")
    public String validateRegistrationForm(@ModelAttribute("registrationUser")RegistrationFormUserDTO userDTO, RedirectAttributes attributes) {
        if (userService.isValueEmpty(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getPasswordRepeat()
        )) {
            returnDTOUponError(userDTO, attributes);
            return "redirect:/register?status=notFilled";
        }
        if (!userService.validatePassword(userDTO.getPassword(), userDTO.getPasswordRepeat())) {
            returnDTOUponError(userDTO, attributes);
            return "redirect:/register?status=passwordNotMatched";
        }
        if (userService.checkUserExistence(userDTO.getUsername())) {
            returnDTOUponError(userDTO, attributes);
            return "redirect:/register?status=userExists";
        }
        userService.convertUserToEntityAndSave(userDTO);
        return "redirect:/login?register=success";
    }

    private void returnDTOUponError(RegistrationFormUserDTO userDTO, RedirectAttributes attributes) {
        userDTO.setPassword("");
        userDTO.setPasswordRepeat("");
        attributes.addFlashAttribute("userDTO", userDTO);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
