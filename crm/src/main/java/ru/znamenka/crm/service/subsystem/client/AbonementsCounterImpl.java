package ru.znamenka.crm.service.subsystem.client;


import com.querydsl.core.Tuple;
import ru.znamenka.crm.represent.page.client.CountOfTraining;
import ru.znamenka.crm.service.BaseExecutor;

import java.util.List;

/**
 * <p>
 * Создан 18.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class AbonementsCounterImpl extends BaseExecutor<Tuple, CountOfTraining> implements AbonementsCounter {

    @Override
    public List<CountOfTraining> countOfTraining(Long clientId) {
        return null;
    }

}
