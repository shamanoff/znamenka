package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.api.page.shedule.ScheduleClientApi;
import ru.znamenka.jpa.repository.EntityRepository;

import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    @Qualifier("dataService")
    private EntityRepository service;

    @GetMapping("/schedule")
    public String getSchedulePage(Model model) {
        List<ScheduleClientApi> list = service.findAll(ScheduleClientApi.class);
        model.addAttribute("clients", list);
        return "schedule";
    }


}
