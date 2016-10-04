package ru.znamenka.service.subsystem.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.User;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.service.ApiStore;

/**
 * Создан 04.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class TrainingService implements ITrainingService {

    @Autowired
    @Qualifier("dataService")
    private ApiStore apiStore;

    public TrainingApi save(TrainingApi api) {
        api.setStatusId(1L);
        api.setTrainerId(getTrainerIdIfExists());
        return apiStore.save(TrainingApi.class, api);
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
