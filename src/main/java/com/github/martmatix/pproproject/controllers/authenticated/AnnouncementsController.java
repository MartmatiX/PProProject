package com.github.martmatix.pproproject.controllers.authenticated;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnnouncementsController {

    @GetMapping(path = "/announcements")
    public String getAnnouncementsPage(){
        return "/authenticated/announcementsPage";
    }

}
