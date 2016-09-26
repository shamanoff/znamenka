package ru.znamenka.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * Основной класс конфигурации приложения
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Configuration
@Import({
        JpaConfig.class
        , GsonAutoConfiguration.class
        , BeanBucketConfig.class
        , WebConfig.class
        , CacheConfig.class
        , BeanConfig.class
        , SecurityConfig.class
        , WebSocketConfig.class
})
@EnableAutoConfiguration
public class AppConfig {
}
