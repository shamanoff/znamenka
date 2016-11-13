package ru.click.core.repository.domain;

import org.springframework.data.repository.CrudRepository;
import ru.click.core.model.ActionLog;

public interface ActionLogRepository extends CrudRepository<ActionLog, Long> {
}
