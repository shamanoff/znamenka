package ru.znamenka.crm.service.subsystem.training;


import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.crm.represent.domain.TrainingApi;
import ru.znamenka.crm.service.ApiStore;
import ru.znamenka.crm.service.BaseExecutor;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.jpa.repository.domain.TrainingRepository;

import static org.springframework.util.Assert.notNull;

/**
 * Создан 04.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class TrainingService extends BaseExecutor<Training, TrainingApi> implements ITrainingService {

    private final ApiStore apiStore;

    private final TrainingRepository repo;

    @Autowired
    public TrainingService(TrainingRepository repo, @Qualifier("dataService") ApiStore apiStore) {
        notNull(repo);
        notNull(apiStore);
        this.repo = repo;
        this.apiStore = apiStore;
    }

    public TrainingApi save(TrainingApi api) {
        api.setStatusId(1L);
        Long id = apiStore.saveAndFlush(TrainingApi.class, api);
        Training training = repo.findOne(id);
        return toApi(training);
    }

    @Override
    public TrainingApi updateTraining(Long status, Long trainingId, Long trainerId) {
        val training = new TrainingApi().setId(trainingId);
        if (status != null) {
            training.setStatusId(status);
        }
        if (trainerId != null) {
            training.setTrainerId(trainerId);
        }
        return apiStore.update(TrainingApi.class, training);
    }


}
