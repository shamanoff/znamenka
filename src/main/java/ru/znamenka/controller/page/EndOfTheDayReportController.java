package ru.znamenka.controller.page;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.api.domain.TrainingStatusApi;
import ru.znamenka.jpa.model.User;
import ru.znamenka.jpa.repository.EntityRepository;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ru.znamenka.jpa.model.QTraining.training;
import static ru.znamenka.jpa.model.QTrainingStatus.trainingStatus;


/**
 * Created by Сережа on 16.08.2016.
 */
@Controller
@RequestMapping("/end-of-day")
public class EndOfTheDayReportController {

    @Autowired
    @Qualifier("dataService")
    private EntityRepository service;

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @RequestMapping(value = "/", method = GET)
    public ModelAndView index(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {
        ModelAndView mv = new ModelAndView();
        if (date == null) {
            mv.setViewName("end-of-day");
            date = now();
        } else {
            mv.setViewName("end-of-day :: training-table");
        }
        List<TrainingApi> trainings = getTrainings(date);
        List<TrainingStatusApi> statuses = service.findAll(TrainingStatusApi.class, trainingStatus.id.eq(1L).not());

        mv.addObject("trainings", trainings);
        mv.addObject("trainingForm", new TrainingApi());
        mv.addObject("statuses", statuses);
        return mv;
    }


    @RequestMapping(value = "/", method = POST)
    public ModelAndView post(TrainingApi trainingForm) {
        TrainingApi api = service.findOne(TrainingApi.class, trainingForm.getId());
        api.setStatusId(trainingForm.getStatusId());
        service.save(TrainingApi.class, api);

        ModelAndView mv = new ModelAndView("end-of-day");
        mv.addObject(getTrainings(now()));
        mv.addObject("statuses", service.findAll(TrainingStatusApi.class, trainingStatus.id.eq(1L).not()));
        return mv;
    }

    private List<TrainingApi> getTrainings(LocalDate date) {
        Predicate predicate = training.status.id.eq(1L)
                .and(training.start.dayOfYear().eq(date.getDayOfYear()))
                .and(training.trainer.id.eq(getTrainerId()));
        return service.findAll(TrainingApi.class, predicate);
    }

    private Long getTrainerId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getTrainerId();
    }

}
