package ru.znamenka.crm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация позволяющая логгировать различные действий пользователя
 * <p>
 * Создан 08.06.2016
 * <p>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLogged {

    /**
     * @return действие пользователя
     */
    String action();

    /**
     * @return сообщение, если действие выполнено успешно
     */
    String successText() default "успешно";

    /**
     * @return сообщение, если действие выполнено неудачно
     */
    String failedText() default "неудачно";
}
