package ru.znamenka.api.converter.domain;

import org.springframework.stereotype.Component;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.api.domain.TrainingReportApi;
import ru.znamenka.jpa.model.Training;

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
public class TrainingReportApiConverter implements ApiConverter<Training, TrainingReportApi> {

    @Override
    public Class<TrainingReportApi> getApiType() {
        return TrainingReportApi.class;
    }

    @Override
    public Class<Training> getEntityType() {
        return Training.class;
    }

    @Override
    public Training convertTo(TrainingReportApi source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TrainingReportApi convert(Training source) {
        return TrainingReportApi
                .builder()
                .clientName(source.getClient().getName())
                .tranerName(source.getTrainer().getName())
                .build();
    }
}
