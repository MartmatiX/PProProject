package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.DTOs.PasswordChangeUserDTO;
import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    private UserService userService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    @PostMapping(path = "/profile/updatePersonal")
    public String updatePersonal(@ModelAttribute UserEntity userFromForm) {
        Optional<UserEntity> user = userService.getUser(userFromForm.getUsername());
        if (user.isEmpty()) {
            return "redirect:/?status=userFetchFail";
        }
        String returnValue = validateUserFromForm(userFromForm, user.get());
        if (returnValue != null) {
            return returnValue;
        }
        UserEntity userFromDatabase = user.get();
        userFromDatabase.setName(userFromForm.getName());
        userFromDatabase.setSurname(userFromForm.getSurname());
        userFromDatabase.setEmail(userFromForm.getEmail());
        userService.saveUser(userFromDatabase);
        return "redirect:/profile/" + userFromForm.getUsername() + "?status=updateSuccess";
    }

    @PostMapping(path = "/profile/changePassword")
    public String updatePassword(@ModelAttribute PasswordChangeUserDTO userFromForm) {
        Optional<UserEntity> user = userService.getUser(userFromForm.getUsername());
        if (user.isEmpty()) {
            return "redirect:/?status=userFetchFail";
        }
        if (!userFromForm.getNewPassword().equals(userFromForm.getNewPasswordRepeat())) {
            return "redirect:/profile/" + userFromForm.getUsername() + "?status=passwordMatchFail";
        }
        if (!encoder.matches(userFromForm.getOriginalPassword(), user.get().getPassword())) {
            return "redirect:/profile/" + userFromForm.getUsername() + "?status=passwordMatchError";
        }
        UserEntity userFromDatabase = user.get();
        userFromDatabase.setPassword(encoder.encode(userFromForm.getNewPassword()));
        userService.saveUser(userFromDatabase);
        return "redirect:/profile/" + userFromForm.getUsername() + "?status=updateSuccess";
    }

    public String validateUserFromForm(UserEntity userFromForm, UserEntity userFromDatabase) {
        if (userFromForm.getRole() != userFromDatabase.getRole()) {
            return "redirect:/profile/" + userFromForm.getUsername() + "?status=roleAltered";
        }
        if (!userFromForm.getUsername().equals(userFromDatabase.getUsername())) {
            return "redirect:/profile/" + userFromForm.getUsername() + "?status=usernameAltered";
        }
        if (!encoder.matches(userFromForm.getPassword(), userFromDatabase.getPassword())) {
            return "redirect:/profile/" + userFromForm.getUsername() + "?status=passwordMatchError";
        }
        return null;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
