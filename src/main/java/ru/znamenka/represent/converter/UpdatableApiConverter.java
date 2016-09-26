package ru.znamenka.represent.converter;


import ru.znamenka.jpa.model.BaseModel;
import ru.znamenka.represent.DomainApi;
import ru.znamenka.represent.UpdatableApi;

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
