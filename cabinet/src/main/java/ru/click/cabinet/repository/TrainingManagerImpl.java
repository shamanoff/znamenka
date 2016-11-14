package ru.click.cabinet.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.click.cabinet.repository.query.QueriesLoader;
import ru.click.core.model.Training;

import java.util.List;

/**
 * <p>
 * Создан 14.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository
public class TrainingManagerImpl implements TrainingManager {

    @Autowired
    private JdbcOperations operations;

    RowMapper<Training> rowMapper = (rs, i) -> new Training();

    @Override
    public List<Training> trainings(Long clientId) {
        String sql = QueriesLoader.trainings;
        return operations.query(sql, rowMapper, clientId);
    }
}
