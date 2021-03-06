package ru.znamenka.service.logging;

import ru.znamenka.jpa.model.LogEntity;

@FunctionalInterface
public interface LoggingService<T extends LogEntity> {

     void log(T record);
}
