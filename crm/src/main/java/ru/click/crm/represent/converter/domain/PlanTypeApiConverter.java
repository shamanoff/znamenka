package ru.click.crm.represent.converter.domain;


import org.springframework.stereotype.Component;
import ru.click.core.represent.converter.ApiConverter;
import ru.click.crm.represent.domain.PlanTypeApi;
import ru.click.core.model.DutyPlanType;

@Component
public class PlanTypeApiConverter implements ApiConverter<DutyPlanType, PlanTypeApi> {
    @Override
    public Class<DutyPlanType> getEntityType() {
        return DutyPlanType.class;
    }

    @Override
    public Class<PlanTypeApi> getApiType() {
        return PlanTypeApi.class;
    }

    @Override
    public DutyPlanType convertTo(PlanTypeApi source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PlanTypeApi convert(DutyPlanType source) {
        return new PlanTypeApi()
                .setId(source.getId())
                .setName(source.getName());
    }
}
