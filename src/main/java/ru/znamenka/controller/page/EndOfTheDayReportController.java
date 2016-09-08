package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.api.domain.TrainingStatusApi;
import ru.znamenka.jpa.model.User;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.page.endofday.EndOfDayPageService;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ru.znamenka.jpa.model.QTrainingStatus.trainingStatus;


/**
 * <p>Контроллер для страницы закрытии смены
 * Создан 16.08.2016
 * <p>

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
    @RequestMapping(method = GET)
    public ModelAndView index(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {
        ModelAndView mv = new ModelAndView();
        if (date == null) {
            mv.setViewName("end-of-day");
            date = now();
        } else {
            mv.setViewName("end-of-day :: training-table");
        }
        List<TrainingApi> trainings = pageService.getTrainings(date, getTrainerId());
        List<TrainingStatusApi> statuses = apiStore.findAll(TrainingStatusApi.class, trainingStatus.id.eq(1L).not());

        mv.addObject("trainings", trainings);
        mv.addObject("trainingForm", new TrainingApi());
        mv.addObject("statuses", statuses);
        return mv;
    }


    @RequestMapping(method = POST)
    public RedirectView post(Long trainingStatus, Long trainingId) {
        if (trainingId != null && trainingStatus != null) {
            TrainingApi api = apiStore.findOne(TrainingApi.class, trainingId);
            api.setStatusId(trainingStatus);
            apiStore.save(TrainingApi.class, api);
        }

        return new RedirectView("/end-of-day");
    }



    private Long getTrainerId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getTrainer().getId();
    }

}
