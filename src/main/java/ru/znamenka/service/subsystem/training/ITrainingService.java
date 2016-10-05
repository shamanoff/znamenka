package ru.znamenka.service.subsystem.training;

import ru.znamenka.represent.domain.TrainingApi;

/**
 * Создан 04.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface ITrainingService {

    TrainingApi save(TrainingApi api);

    TrainingApi updateStatus(Long status, Long trainingId);
}
