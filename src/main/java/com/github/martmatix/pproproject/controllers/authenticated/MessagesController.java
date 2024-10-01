package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.MessageService;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MessagesController {

    private UserService userService;
    private MessageService messageService;

    @GetMapping(path = "/messages")
    public String getMessagesPage(Model model) {
        List<UserEntity> allUsers = userService.getAllActiveUsers(true);
        model.addAttribute("users", allUsers);
        return "authenticated/messagesPage";
    }

    @GetMapping(path = "/message/{username}")
    public String getMessageDetailPage(@PathVariable String username) {

        return "authenticated/messagesDetailPage";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
