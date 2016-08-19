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
        Client client = new Client();
        client.setId(source.getClientId());
        training.setClient(client);

        Trainer trainer= new Trainer();
        trainer.setId(source.getTrainerId());
        training.setTrainer(trainer);

        Purchase purchase = new Purchase();
        purchase.setId(source.getPurchaseId());
        training.setPurchase(purchase);

        training.setStart(source.getStart());

        TrainingStatus status = new TrainingStatus();
        status.setId(source.getStatusId());
        training.setStatus(status);
        return training;
    }

    @Override
    public TrainingApi convert(Training training) {
        return null;
    }







}
