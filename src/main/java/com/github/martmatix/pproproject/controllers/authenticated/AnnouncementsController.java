package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.database.entities.AnnouncementEntity;
import com.github.martmatix.pproproject.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AnnouncementsController {

    private AnnouncementService announcementService;

    @GetMapping(path = "/announcements")
    public String getAnnouncementsPage(Model model, @RequestParam(name = "offset", required = false, defaultValue = "10") Integer offset) {
        model.addAttribute("announcements", announcementService.findAllAnnouncements(offset));
        return "/authenticated/announcementsPage";
    }

    @PostMapping(path = "/announcements/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteById(id);
        return "redirect:/announcements";
    }

    @PostMapping(path = "/announcements/add")
    public String addAnnouncement(@ModelAttribute AnnouncementEntity announcementEntity, Principal principal) {
        announcementEntity.setAdminUsername(principal.getName());
        announcementService.addAnnouncement(announcementEntity);
        return "redirect:/announcements";
    }

    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
}
