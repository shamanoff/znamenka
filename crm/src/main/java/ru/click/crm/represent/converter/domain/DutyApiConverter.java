package ru.click.crm.represent.converter.domain;


import org.springframework.stereotype.Component;
import ru.click.core.represent.converter.UpdatableApiConverter;
import ru.click.crm.represent.domain.DutyApi;
import ru.click.core.model.DutySchedule;

@Component
public class DutyApiConverter implements UpdatableApiConverter<DutySchedule, DutyApi, Long> {

    @Override
    public Class<DutySchedule> getEntityType() {
        return DutySchedule.class;
    }

    @Override
    public Class<DutyApi> getApiType() {
        return DutyApi.class;
    }

    @Override
    public DutySchedule convertTo(DutyApi source) {
        DutySchedule duty = new DutySchedule();
        duty.setId(source.getId());
        duty.setPlanTypeId(source.getPlanTypeId());
        duty.setTrainerId(source.getTrainerId());
        duty.setPlannedStart(source.getPlannedStart());
        duty.setPlannedEnd(source.getPlannedEnd());
        duty.setFactStart(source.getFactStart());
        duty.setFactEnd(source.getFactEnd());
        return duty;
    }

    @Override
    public DutySchedule convertTo(DutyApi source, DutySchedule entity) {
        entity.setFactStart(source.getFactStart());
        entity.setFactEnd(source.getFactEnd());

        return entity;
    }

    @Override
    public DutyApi convert(DutySchedule entity) {
        if (entity == null) return null;
        return new DutyApi()
                .setId(entity.getId())
                .setTrainerId(entity.getTrainerId())
                .setPlannedStart(entity.getPlannedStart())
                .setPlannedEnd(entity.getPlannedEnd())
                .setFactStart(entity.getFactStart())
                .setFactEnd(entity.getFactEnd())
                .setPlanTypeName(entity.getPlanTypeName())
                .setPlanTypeId(entity.getPlanTypeId());
    }
}
