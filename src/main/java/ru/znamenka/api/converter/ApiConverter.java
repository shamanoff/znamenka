package ru.znamenka.api.converter;


import ru.znamenka.api.BaseApi;
import ru.znamenka.jpa.model.BaseModel;
import ru.znamenka.util.DuoConverter;

/**
 * <p>
 *     Интерфейс конвертера для преобразования моделей {@link BaseModel}
 * в представления {@link BaseApi}. Служит для связи представления
 * и модели.
 * <p>
 * Связан с классом представления как один-ко-одному,
 * т.е. на каждое представление строго один конвертер модели в это представление
 * и обратно.
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface ApiConverter<E extends BaseModel, A > extends DuoConverter<E, A>, ApiInfo<A> {

    /**
     * Возвращает тип модели
     * @return тип модели
     */
    Class<E> getEntityType();

}
