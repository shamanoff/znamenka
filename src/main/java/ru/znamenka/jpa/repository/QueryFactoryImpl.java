package ru.znamenka.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * Реализация {@link QueryFactory}
 * <p>
 * Создан 21.06.2016
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository("mainExecutor")
public class QueryFactoryImpl implements QueryFactory {

    /**
     * Менеджер для упраления бизнес-моделями
     */
    private final EntityManager entityManager;

    public QueryFactoryImpl(@Autowired EntityManager em) {
        this.entityManager = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> JPAQuery<T> getJpaQuery() {
        return new JPAQuery<>(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Page<T> executeQueryList(@NotNull JPAQuery<T> query, @Nullable Pageable pageable) {
        notNull(query);
        if (pageable != null) {
            query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        long total = query.fetchCount();
        List<T> content;
        if (pageable == null || total > pageable.getOffset()) {
            content = query.fetch();
        } else {
            content = emptyList();
        }

        return new PageImpl<>(content, pageable, total);
    }

    @PreDestroy
    public void destroy() {
        this.entityManager.close();
    }

}
