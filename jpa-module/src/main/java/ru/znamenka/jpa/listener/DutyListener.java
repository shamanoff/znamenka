package ru.znamenka.jpa.listener;

import ru.znamenka.jpa.model.DutySchedule;

import javax.persistence.PostLoad;

public class DutyListener {

    @PostLoad
    public void setPlanTypeName(DutySchedule duty) {
        if (duty.getPlanType() != null) {
            duty.setPlanTypeName(duty.getPlanType().getName());
        }
    }
}
