package ru.znamenka.api.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.jpa.model.*;

/**
 * Created by Сережа on 10.08.2016.
 */
@Component
public class TrainingApiConverter implements ApiConverter<Training,TrainingApi>{

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
        api.setClientId(training.getClient().getId());
        api.setClientName(training.getClient().getName() + " " + training.getClient().getSurname());
        api.setTrainerId(training.getTrainer().getId());
        api.setId(training.getId());
        api.setPurchaseId(training.getPurchase().getId());
        api.setStatusId(training.getStatus().getId());
        api.setStart(training.getStart());

        return api;
    }







}
