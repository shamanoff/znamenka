package ru.znamenka.config;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.znamenka.api.converter.ApiConverter;
import ru.znamenka.jpa.repository.QueryDslRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *      Конфигурация корзинок с бинами.
 *      Используется для динамического поиска необходимых классов в пределе одного метода
 * @author Евгений Уткин (Eugene Utkin)
 *
 */
@Configuration
public class BeanBucketConfig {

    /**
     * Контейнер бинов
     */
    @Autowired
    private ListableBeanFactory ctx;


    /**
     * Корзинка, в которой хранятся ссылки на Созданные репозитории Spring Data JPA
     * с доступом по типу модели
     * @return корзинка репозиториев
     */
    @Bean(destroyMethod = "clear")
    public Map<Class, QueryDslRepository> repositoryBucket() {
        final Collection<QueryDslRepository> repositories = ctx.getBeansOfType(QueryDslRepository.class).values();
        Map<Class, QueryDslRepository> map = new ConcurrentHashMap<>(repositories.size());
        for (QueryDslRepository repository : repositories) {
            map.put(repository.getJavaType(), repository);
        }
        return map;
    }

    /**
     * Корзинка, в которой хранятся ссылки на Созданные конвертеры моделей в представления {@link ApiConverter}
     * @return корзинка конверторов
     */
    @Bean(destroyMethod = "clear")
    public Map<Class, ApiConverter> convertersBucket() {
        final Collection<ApiConverter> converters = ctx.getBeansOfType(ApiConverter.class).values();
        Map<Class, ApiConverter> map = new ConcurrentHashMap<>(converters.size());
        for (ApiConverter converter : converters) {
            map.put(converter.getApiType(), converter);
        }
        return map;
    }
}
