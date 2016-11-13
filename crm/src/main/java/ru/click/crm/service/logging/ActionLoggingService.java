package ru.click.crm.service.logging;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.click.core.model.ActionLog;
import ru.click.core.repository.domain.ActionLogRepository;

import static org.springframework.util.Assert.notNull;

@Service("actionLogging")
public class ActionLoggingService implements LoggingService<ActionLog> {

    /**
     * Репозиторий для записи лога
     */
    @Autowired
    private ActionLogRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    @Async
    public void log(ActionLog record) {
        notNull(record);
        repo.save(record);
    }

    /**
     * Сеттер для внедрения репозитория
     *
     * @param repo репозиторий для записи лога
     * @return текущий объект
     */
    @Autowired
    public ActionLoggingService setRepo(ActionLogRepository repo) {
        notNull(repo);
        this.repo = repo;
        return this;
    }
}
