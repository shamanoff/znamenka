package ru.znamenka.crm.represent.converter;


import ru.znamenka.crm.represent.DomainApi;
import ru.znamenka.crm.represent.UpdatableApi;
import ru.znamenka.jpa.model.BaseModel;

/**
 * <p>
 * Интерфейс конвертера для преобразования моделей {@link BaseModel}
 * в представления {@link DomainApi}. Служит для связи представления
 * и модели.
 * <p>
 * Связан с классом представления как один-ко-одному,
 * т.е. на каждое представление строго один конвертер модели в это представление
 * и обратно.
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface UpdatableApiConverter<E extends BaseModel<ID>, A extends DomainApi & UpdatableApi<ID>, ID> extends ApiConverter<E, A> {

    E convertTo(A source, E entity);

}
