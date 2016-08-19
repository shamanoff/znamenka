package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by Сережа on 16.08.2016.
 */
@Controller
public class EndOfTheDayReportController {



    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @GetMapping("/enddayreport")
    public String index(Model model) {

        return "report";
    }

}
