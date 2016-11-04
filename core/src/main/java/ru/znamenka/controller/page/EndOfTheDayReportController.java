package ru.znamenka.controller.page;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateOperation;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.represent.domain.DutyApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.domain.TrainingStatusApi;
import ru.znamenka.represent.page.endofday.Report;
import ru.znamenka.represent.page.endofday.Status;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.page.endofday.EndOfDayPageService;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.ONE;
import static java.time.LocalDate.now;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ru.znamenka.jpa.model.QDutySchedule.dutySchedule;
import static ru.znamenka.jpa.model.QTrainingStatus.trainingStatus;


/**
 * <p>Контроллер для страницы закрытии смены
 * Создан 16.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
@RequestMapping("/end-of-day")
public class EndOfTheDayReportController {

    private final ApiStore apiStore;

    private final EndOfDayPageService pageService;

    @Autowired
    public EndOfTheDayReportController(@Qualifier("dataService") ApiStore apiStore, EndOfDayPageService pageService) {
        notNull(apiStore);
        notNull(pageService);
        this.apiStore = apiStore;
        this.pageService = pageService;
    }

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @GetMapping
    public ModelAndView index(
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
            @ModelAttribute Long trainerId
    ) {
        ModelAndView mv = new ModelAndView();
        initialize(date, trainerId, mv);
        mv.addObject("trainingForm", new TrainingApi());
        return mv;
    }

    @GetMapping("/trainings")
    public ModelAndView trainingsTable(
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
            @ModelAttribute Long trainerId
    ) {
        ModelAndView mv = new ModelAndView("end-of-day :: training-table");
        initialize(date, trainerId, mv);
        return mv;
    }

    private void initialize(LocalDate date, Long trainerId, ModelAndView mv) {
        if (date == null) date = now();
        List<TrainingApi> trainings = pageService.getTrainings(date, trainerId);
        List<TrainingStatusApi> statuses = apiStore.findAll(TrainingStatusApi.class, trainingStatus.id.eq(1L).not());
        DateOperation<LocalDate> dateOperation = Expressions
                .dateOperation(LocalDate.class, Ops.DateTimeOps.DATE, dutySchedule.plannedStart);
        Predicate currentDutyP = dutySchedule.trainerId.eq(trainerId)
                .and(dateOperation.eq(date))
                .and(ONE.eq(dutySchedule.planTypeId.intValue()))
                .and(dutySchedule.factStart.isNull());
        DutyApi duty = apiStore.findOne(DutyApi.class, currentDutyP);

        boolean hasPlannedTrainings = trainings.stream().anyMatch(t -> Long.valueOf(1).equals(t.getStatusId()));
        mv.addObject("trainings", trainings);
        mv.addObject("statuses", statuses);
        mv.addObject("duty", duty);
        mv.addObject("hasPlannedTrainings", hasPlannedTrainings);
    }


    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    // TODO: 18.10.2016 перенести в сервис и добавить Transactional
    public ResponseEntity<String> post(@RequestBody Report report) {
        for (Status status : report.getStatuses()) {
            TrainingApi trainingApi = apiStore.findOne(TrainingApi.class, status.getTrainingId());
            trainingApi.setStatusId(status.getStatus());
            trainingApi.setComment(status.getComment());
            apiStore.update(TrainingApi.class, trainingApi);

            if (report.getDutyId() != null) {
                DutyApi dutyApi = apiStore.findOne(DutyApi.class, dutySchedule.id.eq(report.getDutyId()));
                dutyApi.setFactStart(report.getFactStart());
                dutyApi.setFactEnd(report.getFactEnd());
                apiStore.update(DutyApi.class, dutyApi);
            }
        }
        return ResponseEntity.ok("{}");
    }


}
