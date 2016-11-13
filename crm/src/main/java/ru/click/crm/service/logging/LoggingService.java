package ru.click.crm.service.logging;


import ru.click.core.model.LogEntity;

@FunctionalInterface
public interface LoggingService<T extends LogEntity> {

     void log(T record);
}
