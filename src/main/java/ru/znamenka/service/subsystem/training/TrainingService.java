package ru.znamenka.service.subsystem.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.jpa.repository.domain.TrainingRepository;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.BaseExecutor;

/**
 * Создан 04.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class TrainingService extends BaseExecutor<Training, TrainingApi> implements ITrainingService {

    @Autowired
    @Qualifier("dataService")
    private ApiStore apiStore;

    @Autowired
    private TrainingRepository repo;

    public TrainingApi save(TrainingApi api) {
        api.setStatusId(1L);
        Long id = apiStore.saveAndFlush(TrainingApi.class, api);
        Training training = repo.findOne(id);
        return toApi(training);
    }

    @Override
    public TrainingApi updateStatus(Long status, Long trainingId) {
        TrainingApi api = apiStore.update(TrainingApi.class, new TrainingApi().setStatusId(status).setId(trainingId));
        return api;
    }


}
