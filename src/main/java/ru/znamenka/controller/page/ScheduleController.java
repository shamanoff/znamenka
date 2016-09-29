package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.jpa.model.User;
import ru.znamenka.represent.CalendarEvent;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.page.schedule.SubscriptionApi;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.client.ClientService;
import ru.znamenka.service.page.schedule.ScheduleLoadService;
import ru.znamenka.service.page.schedule.SubscriptionPageService;

import javax.validation.Valid;
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

    private final ClientService clientService;

    @Autowired
    public ScheduleController(
            SubscriptionPageService pageService,
            @Qualifier("dataService") ApiStore service,
            ScheduleLoadService eventsService,
            SimpMessageSendingOperations mesTemplate,
            ClientService clientService) {
        notNull(pageService);
        notNull(service);
        notNull(eventsService);
        notNull(mesTemplate);
        notNull(clientService);
        this.pageService = pageService;
        this.service = service;
        this.eventsService = eventsService;
        this.mesTemplate = mesTemplate;
        this.clientService = clientService;
    }

    /**
     * Метод для инициализации страницы "Запись на тренировку".
     * Загружает список клиентов, у которых есть активные абонементы
     *
     * @return страница templates/schedule.html
     */
    @GetMapping
    public ModelAndView getSchedulePage() {
        ModelAndView mv = new ModelAndView("schedule");
        List<ClientApi> list = clientService.allActiveClients();
        mv.addObject("clients", list);
        mv.addObject("training", new TrainingApi());
        return mv;
    }

    /**
     * API для подгрузки абонементов клиента по его id
     *
     * @param clientId уникальный идентификатор клиента
     * @return список абонементов
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionApi>> getSubscriptions(@RequestParam("clientId") Long clientId) {
        if (clientId == null) {
            return badRequest().body(emptyList());
        }
        List<SubscriptionApi> subscriptions = pageService.getSubscriptionByClientId(clientId);
        return ok(subscriptions);
    }

    /**
     * API для бронивания тренировки для пользователя.
     * Тренировка записывается в базу со статусом "Зарезервирована" и номером
     * тренера.
     * <p>Далее, тренировка постится в гугль календарь и затем по веб сокету
     * постится в раписание студии (смотри {@link CalendarController}
     *
     * @param training      параметры тренировки
     * @param bindingResult результат валидации
     * @return 200 если тренировка успешно забронирована, 400 в ином случае
     */
    @RequestMapping(
            path = "/",
            method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset:utf-8",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TrainingApi> bookTraining(@Valid TrainingApi training, BindingResult bindingResult) {
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

    /**
     * API, которое является event-source для full-calendar'я на фронт енде.
     * Получает список событий и отдает на фронт-енд с фильтром по дате
     *
     * @param start начальная дата поиска событий
     * @param end   конечная дата
     * @return список событий
     */
    @GetMapping(path = "/events", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CalendarEvent>> events(Date start, Date end) {
        return ok(eventsService.loadEvents(start, end));
    }

    /**
     * API для общедоступного расписания студии с фильтром по дате
     * @param start начальная дата поиска событий
     * @param end   конечная дата
     * @return список событий
     */
    @GetMapping(path = "/events/busy", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CalendarEvent>> eventsBusy(Date start, Date end) {
        return ok(eventsService.loadEventsBusy(start, end));
    }

    /**
     * Получает тренера из security контекста
     * @return уникальный идентификатор пользователя-тренера
     */
    private Long getTrainerIdIfExists() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getTrainer().getId();
    }

}
