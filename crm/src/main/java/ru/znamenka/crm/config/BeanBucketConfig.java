package ru.znamenka.crm.config;


import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.znamenka.jpa.repository.EntityRepository;
import ru.znamenka.jpa.represent.ApiStore;
import ru.znamenka.jpa.represent.converter.ApiConverter;
import ru.znamenka.jpa.represent.converter.UpdatableApiConverter;
import ru.znamenka.jpa.represent.impl.ConvertEntityService;

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

    @Bean(destroyMethod = "clear")
    public Map<Class, UpdatableApiConverter> convertersBucketForUpdatable() {
        final Collection<UpdatableApiConverter> converters = ctx.getBeansOfType(UpdatableApiConverter.class).values();
        Map<Class, UpdatableApiConverter> map = new ConcurrentHashMap<>(converters.size());
        for (UpdatableApiConverter converter : converters) {
            map.put(converter.getApiType(), converter);
        }
        return map;
    }

    @Bean(name = "convertService")
    @Autowired
    public ApiStore convertService(EntityRepository repo) {
        return new ConvertEntityService(repo, convertersBucket(), convertersBucketForUpdatable());
    }
}
