package com.github.martmatix.pproproject.controllers.authenticated;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
import com.github.martmatix.pproproject.services.RecordService;
import com.github.martmatix.pproproject.services.RecordTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class RecordsController {

    private RecordService recordService;
    private RecordTypeService recordTypeService;

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

        int totalTime = 0;
        int approvedTime = 0;
        for (RecordEntity record : records) {
            totalTime += record.getLength();
            if (record.isApproved()) {
                approvedTime += record.getLength();
            }
        }
        model.addAttribute("totalTime", totalTime);
        model.addAttribute("approvedTime", approvedTime);

        model.addAttribute("recordTypes", recordTypeService.findAllRecordTypes());

        model.addAttribute("records", records);
        return "/authenticated/recordsPageDay";
    }

    @PostMapping(path = "/records/delete/")
    public String deleteRecord(@RequestParam String date, @RequestParam Long id) {
        Optional<RecordEntity> byId = recordService.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/records/" + date + "?status=unableToDelete";
        }
        recordService.deleteRecord(byId.get());
        return "redirect:/records/" + date;
    }

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    @Autowired
    public void setRecordTypeService(RecordTypeService recordTypeService) {
        this.recordTypeService = recordTypeService;
    }
}
