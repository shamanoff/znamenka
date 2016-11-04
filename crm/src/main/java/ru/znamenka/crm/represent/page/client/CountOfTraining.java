package ru.znamenka.crm.represent.page.client;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.znamenka.crm.represent.Api;

/**
 * <p>
 * Создан 18.10.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Getter
@Setter
@Accessors(chain = true)
public class CountOfTraining implements Api {

    private Long purchaseId;

    private String abonement;

    private int count;

    public static CountOfTraining create() {
        return new CountOfTraining();
    }
}
