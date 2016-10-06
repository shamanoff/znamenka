package ru.znamenka.represent.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.represent.converter.UpdatableApiConverter;
import ru.znamenka.represent.domain.TrainingApi;

/**
 * Created by Сережа on 10.08.2016.
 */
@Component
public class TrainingApiConverter implements UpdatableApiConverter<Training, TrainingApi, Long> {

    @Override
    public Class<TrainingApi> getApiType() {
        return TrainingApi.class;
    }

    @Override
    public Class<Training> getEntityType() {
        return Training.class;
    }

    @Override
    public Training convertTo(TrainingApi source) {
        Training training = new Training();
        training.setClientId(source.getClientId());
        training.setTrainerId(source.getTrainerId());
        training.setPurchaseId(source.getPurchaseId());
        training.setStart(source.getStart());
        training.setStatusId(source.getStatusId());
        return training;
    }

    @Override
    public TrainingApi convert(Training training) {
        TrainingApi api = new TrainingApi();
        api.setClientId(training.getClientId());
        Client client = training.getClient();
        api.setClientName(client.getName() + " " + client.getSurname());
        api.setTrainerId(training.getTrainer().getId());
        api.setTrainerName(training.getTrainer().getName());
        api.setId(training.getId());
        api.setPurchaseId(training.getPurchase().getId());
        api.setStatusId(training.getStatus().getId());
        api.setStatusName(training.getStatus().getName());
        api.setStart(training.getStart());

        return api;
    }


    @Override
    public Training convertTo(TrainingApi source, Training entity) {
        entity.setStatusId(source.getStatusId());
        entity.setStart(source.getStart());
        entity.setTrainerId(source.getTrainerId());

        return entity;
    }
}
