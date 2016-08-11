package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.api.page.shedule.ScheduleClientApi;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.service.page.schedule.ClientAbonementService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ScheduleController {

    @Autowired
    @Qualifier("dataService")
    private EntityRepository service;
    @Autowired
    private ClientAbonementService abonementService;

    @GetMapping("/schedule")
    public String getSchedulePage(Model model) {
        List<ScheduleClientApi> list = service.findAll(ScheduleClientApi.class);
        model.addAttribute("clients", list);
        model.addAttribute("training", new TrainingApi());
        return "schedule";
    }
    @GetMapping("/schedule/abonement")
    public ResponseEntity<Map<Long, String>> getAbonementsByClient(@RequestParam("clientId") Long clientId){
        Map<Long, String> abonements = abonementService.getAbonementByClient(clientId);
        return ok(abonements);
    }

    @RequestMapping(path="/schedule/book",method = POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset:utf-8")
    public RedirectView bookTraining(@ModelAttribute("training") TrainingApi trainingApi, Model model){
        service.save(TrainingApi.class , trainingApi);
        model.addAttribute("training", trainingApi);
        return  new RedirectView("/schedule/");

    }
}
