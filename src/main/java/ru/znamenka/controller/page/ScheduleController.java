package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.represent.CalendarEvent;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainerApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.service.subsystem.client.ClientService;
import ru.znamenka.service.subsystem.training.EventService;
import ru.znamenka.service.subsystem.training.ITrainingService;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/training")
public class ScheduleController {

    private final ITrainingService service;

    private final EventService eventService;

    private final ClientService clientService;

    @Autowired
    public ScheduleController(
            EventService eventService,
            ITrainingService service,
            ClientService clientService) {
        notNull(eventService);
        notNull(service);
        notNull(clientService);
        this.eventService = eventService;
        this.service = service;
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
        List<ClientApi> list = clientService.activeClients();
        mv.addObject("clientNew", ClientApi.empty());
        mv.addObject("clients", list);
        mv.addObject("training", TrainingApi.empty());
        return mv;
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
            path = "club-client",
            method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset:utf-8",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TrainingApi> bookTraining(@Valid TrainingApi training, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                service.save(training);
                eventService.postToCalendar(training);
                return ok(training);
            }
        } catch (Exception e) {
            // for transactional both method
        }
        return badRequest().body(training);
    }

    @RequestMapping(path = "new-client", method = POST)
    public ResponseEntity<TrainingApi> bookTraining(@Valid ClientApi client, LocalDateTime startTraining, BindingResult br) {
        if (br.hasErrors()) return badRequest().body(TrainingApi.empty());
        client = clientService.store().save(ClientApi.class, client);
        TrainingApi training = new TrainingApi();
        training.setClientId(client.getId());
        training.setStart(startTraining);
        training.setStatusId(1L);

        return ok(training);
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
        return ok(eventService.loadEvents(start, end));
    }

    /**
     * API для общедоступного расписания студии с фильтром по дате
     *
     * @param start начальная дата поиска событий
     * @param end   конечная дата
     * @return список событий
     */
    @GetMapping(path = "/events/busy", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CalendarEvent>> eventsBusy(Date start, Date end) {
        return ok(eventService.loadEventsBusy(start, end));
    }


}
