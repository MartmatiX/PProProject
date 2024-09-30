package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MessagesController {

    private UserService userService;

    @GetMapping(path = "/messages")
    public String getMessagesPage(Model model) {
        List<UserEntity> allUsers = userService.getAllActiveUsers(true);
        model.addAttribute("users", allUsers);
        return "authenticated/messagesPage";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
