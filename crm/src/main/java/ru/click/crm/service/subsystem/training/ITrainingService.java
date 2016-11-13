package ru.click.crm.service.subsystem.training;


import ru.click.crm.represent.domain.TrainingApi;

/**
 * Создан 04.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface ITrainingService {

    TrainingApi save(TrainingApi api);

    TrainingApi updateTraining(Long status, Long trainingId, Long trainerId);
}
