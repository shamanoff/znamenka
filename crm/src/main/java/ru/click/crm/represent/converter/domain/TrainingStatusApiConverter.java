package ru.click.crm.represent.converter.domain;


import org.springframework.stereotype.Component;
import ru.click.crm.represent.domain.TrainingStatusApi;
import ru.click.core.represent.converter.ApiConverter;
import ru.click.core.model.TrainingStatus;

/**
 * <p>
 * <p>
 * Создан 19.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class TrainingStatusApiConverter implements ApiConverter<TrainingStatus, TrainingStatusApi> {

    @Override
    public Class<TrainingStatusApi> getApiType() {
        return TrainingStatusApi.class;
    }

    @Override
    public Class<TrainingStatus> getEntityType() {
        return TrainingStatus.class;
    }

    @Override
    public TrainingStatus convertTo(TrainingStatusApi source) {
        TrainingStatus status = new TrainingStatus();
        status.setId(source.getId());
        status.setName(source.getName());
        return status;
    }

    @Override
    public TrainingStatusApi convert(TrainingStatus source) {
        TrainingStatusApi status = new TrainingStatusApi();
        status.setId(source.getId());
        status.setName(source.getName());
        return status;
    }
}
