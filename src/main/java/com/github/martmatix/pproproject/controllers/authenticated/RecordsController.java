package com.github.martmatix.pproproject.controllers.authenticated;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecordsController {

    @GetMapping(path = "/records")
    public String getRecordsPage() {
        return "/authenticated/recordsPage";
    }

    @GetMapping(path = "/records/{date}")
    public String getRecordsDate(@PathVariable String date) {
        System.out.println(date);
        return "/authenticated/recordsPageDay";
    }

}
