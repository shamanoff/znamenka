package ru.znamenka.controller.page;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.api.domain.TrainingStatusApi;
import ru.znamenka.jpa.model.User;
import ru.znamenka.jpa.repository.EntityRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.znamenka.jpa.model.QTraining.training;
import static ru.znamenka.jpa.model.QTrainingStatus.trainingStatus;


/**
 * Created by Сережа on 16.08.2016.
 */
@Controller
public class EndOfTheDayReportController {

    @Autowired
    @Qualifier("dataService")
    private EntityRepository service;

    @PreAuthorize("hasRole('ROLE_TRAINER')")
    @GetMapping("/end-of-day")
    public String index(Model model) {
        LocalDate date = LocalDate.now();
        Predicate predicate = training.status.id.eq(1L)
                .and(training.start.dayOfYear().eq(date.getDayOfYear()))
                .and(training.trainer.id.eq(getTrainerId()));
        List<TrainingApi> trainings = service.findAll(TrainingApi.class, predicate);

        List<TrainingStatusApi> statuses = service.findAll(TrainingStatusApi.class, trainingStatus.id.eq(1L).not());
        model.addAttribute("trainings", trainings);
        model.addAttribute("statuses", statuses);
        return "end-of-day";
    }

    private Long getTrainerId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getTrainerId();
    }

}
