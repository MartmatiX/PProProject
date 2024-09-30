package com.github.martmatix.pproproject.controllers.authenticated;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessagesController {

    @GetMapping(path = "/messages")
    public String getMessagesPage() {
        return "authenticated/messagesPage";
    }

}
