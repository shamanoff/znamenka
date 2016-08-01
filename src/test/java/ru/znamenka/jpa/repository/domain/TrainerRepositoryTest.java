package ru.znamenka.jpa.repository.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.znamenka.api.custom.TrainingReportApi;
import ru.znamenka.jpa.model.Trainer;
import ru.znamenka.jpa.model.Training;
import ru.znamenka.jpa.repository.EntityRepository;

import java.util.List;

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
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class TrainerRepositoryTest {

    @Autowired
    @Qualifier("facadeRepository")
    private EntityRepository repo;

    @Autowired
    @Qualifier("convertService")
    private EntityRepository service;

    @Test
    public void test() throws  Exception {

        List<TrainingReportApi> report = service.findAll(TrainingReportApi.class);

        List<Trainer> trainers = repo.findAll(Trainer.class);
        List<Training> trainings = repo.findAll(Training.class);

    }



}