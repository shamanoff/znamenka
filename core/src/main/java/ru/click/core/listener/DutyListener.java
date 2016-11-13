package ru.click.core.listener;

import ru.click.core.model.DutySchedule;

import javax.persistence.PostLoad;

public class DutyListener {

    @PostLoad
    public void setPlanTypeName(DutySchedule duty) {
        if (duty.getPlanType() != null) {
            duty.setPlanTypeName(duty.getPlanType().getName());
        }
    }
}
