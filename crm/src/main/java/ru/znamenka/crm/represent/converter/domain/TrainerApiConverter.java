package ru.znamenka.crm.represent.converter.domain;


import org.springframework.stereotype.Component;
import ru.znamenka.jpa.represent.converter.ApiConverter;
import ru.znamenka.crm.represent.domain.TrainerApi;
import ru.znamenka.jpa.model.Trainer;

/**
 * <p>
 * <p>
 * Создан 01.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class TrainerApiConverter implements ApiConverter<Trainer, TrainerApi> {

    @Override
    public Class<TrainerApi> getApiType() {
        return TrainerApi.class;
    }

    @Override
    public Class<Trainer> getEntityType() {
        return Trainer.class;
    }

    @Override
    public Trainer convertTo(TrainerApi source) {
        Trainer trainer = new Trainer();
        trainer.setId(source.getId());
        trainer.setName(source.getName());

        return trainer;
    }

    @Override
    public TrainerApi convert(Trainer source) {
        return TrainerApi
                .builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
