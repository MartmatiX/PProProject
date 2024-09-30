package com.github.martmatix.pproproject.controllers.authenticated;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecordsController {

    @GetMapping(path = "/records")
    public String getRecordsPage() {
        return "/authenticated/recordsPage";
    }

}
