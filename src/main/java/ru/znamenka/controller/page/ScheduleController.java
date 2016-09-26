package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.znamenka.represent.CalendarEvent;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.page.schedule.SubscriptionApi;
import ru.znamenka.jpa.model.User;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.page.schedule.ScheduleLoadService;
import ru.znamenka.service.page.schedule.SubscriptionPageService;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final ApiStore service;

    private final SubscriptionPageService pageService;

    private final ScheduleLoadService eventsService;

    private final SimpMessageSendingOperations mesTemplate;

    @Autowired
    public ScheduleController(
            SubscriptionPageService pageService,
            @Qualifier("dataService") ApiStore service,
            ScheduleLoadService eventsService,
            SimpMessageSendingOperations mesTemplate
    ) {
        notNull(pageService);
        notNull(service);
        notNull(eventsService);
        notNull(mesTemplate);
        this.pageService = pageService;
        this.service = service;
        this.eventsService = eventsService;
        this.mesTemplate = mesTemplate;
    }

    @GetMapping
    public String getSchedulePage(Model model) {
        List<ClientApi> list = service.findAll(ClientApi.class);
        model.addAttribute("clients", list);
        model.addAttribute("training", new TrainingApi());
        return "schedule";
    }

    @GetMapping("/clients")
    public String getClientPage(Model model) {
        List<ClientApi> list = service.findAll(ClientApi.class);
        model.addAttribute("clients", list);
        return "clients";
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionApi>> getSubscriptions(@RequestParam("clientId") Long clientId) {
        if (clientId == null) {
            return badRequest().body(emptyList());
        }
        List<SubscriptionApi> subscriptions = pageService.getSubscriptionByClientId(clientId);
        return ok(subscriptions);
    }

    @RequestMapping(
            path = "/",
            method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset:utf-8",
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<TrainingApi> bookTraining(@Valid TrainingApi training, BindingResult bindingResult) throws IOException {
        if (!bindingResult.hasErrors()) {
            training.setStatusId(1L);
            training.setTrainerId(training.getTrainerId() != null ? training.getTrainerId() : getTrainerIdIfExists());
            pageService.postToCalendar(training);

            CalendarEvent event = new CalendarEvent("Занято", training.getStart(), training.getEnd());
            mesTemplate.convertAndSend("/calendar/event", event);
            service.save(TrainingApi.class, training);
            return ok(training);
        }
        return badRequest().body(training);

    }

    @GetMapping(path = "/events", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CalendarEvent> events(Date start, Date end) {
        return eventsService.loadEvents(start, end);
    }

    @GetMapping(path = "/events/busy", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CalendarEvent> eventsBusy(Date start, Date end) {
        return eventsService.loadEventsBusy(start, end);
    }

    private Long getTrainerIdIfExists() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getTrainer().getId();
    }

}
