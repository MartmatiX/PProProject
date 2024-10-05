package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
import com.github.martmatix.pproproject.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RecordsController {

    private RecordService recordService;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping(path = "/records")
    public String getRecordsPage() {
        return "/authenticated/recordsPage";
    }

    @GetMapping(path = "/records/{date}")
    public String getRecordsDate(@PathVariable String date, Principal principal, Model model) {
        Date dateFromString;
        try {
            dateFromString = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return "redirect:/records?status=dateParseError";
        }
        List<RecordEntity> records = recordService.findRecordsByUsernameAndDate(principal.getName(), dateFromString);
        model.addAttribute("records", records);
        return "/authenticated/recordsPageDay";
    }

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
}
