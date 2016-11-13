package ru.click.crm.service.page.endofday;


import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.click.crm.represent.domain.TrainingApi;
import ru.click.core.represent.ApiStore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;
import static org.springframework.util.Assert.notNull;
import static ru.click.core.model.QTraining.training;

/**
 * <p>
 * <p>
 * Создан 26.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class EndOfDayPageService {

    private final ApiStore apiStore;

    @Autowired
    public EndOfDayPageService(@Qualifier("dataService") ApiStore apiStore) {
        notNull(apiStore);
        this.apiStore = apiStore;
    }



    public List<TrainingApi> getTrainings(LocalDate date, Long trainerId) {
        Predicate predicate = training
                .start.between(LocalDateTime.of(date, MIN), LocalDateTime.of(date, MAX))
                .and(training.trainer.id.eq(trainerId));
        return apiStore.findAll(TrainingApi.class, predicate);
    }

}
