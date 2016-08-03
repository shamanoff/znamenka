package ru.znamenka.config;

import lombok.val;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.znamenka.util.locale.ExtResourceBundleSource;

/**
 * <p>
 *     Основной класс конфигурации приложения
 * @author Евгений Уткин (Eugene Utkin)
 */
@Configuration
@Import({
    JpaConfig.class
        , GsonAutoConfiguration.class
        , BeanBucketConfig.class
        , WebConfig.class
        , CacheConfig.class
})
@ComponentScan("ru.znamenka.*")
@EnableAutoConfiguration
public class AppConfig {


    /**
     * Proxy для перехвата исключений
     * @return возвращает proxy
     */
    @Bean(name = "dataService")
    public ProxyFactoryBean facadeRepoProxy() {
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        proxy.setInterceptorNames("repositoryThrowsAdvice");
        proxy.setTargetName("convertService");

        return proxy;
    }

    /**
     * Создает бин для доступа к пулу сообщений.
     * Используется для локализации
     * @return бин для доступа к локализованным сообщениям
     */
    @Bean
    public MessageSource messageSource() {
        val source = new ExtResourceBundleSource();
        source.setBasename("static\\locale\\messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
