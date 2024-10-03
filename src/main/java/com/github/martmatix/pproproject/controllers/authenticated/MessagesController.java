package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.database.entities.MessageEntity;
import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.services.MessageService;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
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
    public String getMessageDetailPage(@PathVariable String username, Principal principal, Model model) {
        List<MessageEntity> messagesBetweenUsers = messageService.findMessagesBetweenUsers(principal.getName(), username);
        model.addAttribute("messages", messagesBetweenUsers);
        return "authenticated/messagesDetailPage";
    }

    @PostMapping(path = "/messages/send/{username}")
    public String sendMessage(@PathVariable String username, @ModelAttribute MessageEntity message, Principal principal) {
        if (message.getMessage().trim().isEmpty() || message.getMessage().trim().isBlank()) {
            return "redirect:/message/" + username + "?status=messageEmpty";
        }
        message.setSenderName(principal.getName());
        message.setReceiverName(username);
        messageService.saveMessage(message);
        return "redirect:/message/" + username;
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
