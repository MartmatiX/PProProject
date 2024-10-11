package com.github.martmatix.pproproject.controllers.admin;

import com.github.martmatix.pproproject.database.entities.RecordEntity;
import com.github.martmatix.pproproject.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminRecordController {

    private RecordService recordService;

    @PostMapping(path = "/admin/viewRecords/approve")
    public String approveRecord(@RequestParam Long recordId, @RequestParam String user) {
        Optional<RecordEntity> byId = recordService.findById(recordId);
        if (byId.isEmpty()) {
            return "redirect:/admin/viewRecords/" + user + "?status=recordDoesNotExist";
        }
        byId.get().setApproved(true);
        recordService.saveRecord(byId.get());
        return "redirect:/admin/viewRecords/" + user + "?status=approved";
    }

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
}
