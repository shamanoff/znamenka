package ru.click.core.repository.domain;

import ru.click.core.model.DutySchedule;
import ru.click.core.repository.QueryDslRepository;

public interface DutyRepository extends QueryDslRepository<DutySchedule, Long> {
}
