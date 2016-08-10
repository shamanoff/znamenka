package ru.znamenka.api.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.jpa.model.Client;
import ru.znamenka.jpa.model.Purchase;
import ru.znamenka.jpa.model.Trainer;
import ru.znamenka.jpa.model.Training;

import java.security.Timestamp;

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
        client.setId(source.getClient());
        training.setClient(client);

        Trainer trainer= new Trainer();
        //trainer.setId(source.getTrainer());
        //// TODO: 10.08.2016 поменять тренера

        trainer.setId(1L);
        training.setTrainer(trainer);

        Purchase purchase = new Purchase();
        purchase.setId(source.getPurchase());
        training.setPurchase(purchase);

        training.setStart(source.getStart());
        return training;
    }

    @Override
    public TrainingApi convert(Training training) {
        return null;
    }







}
