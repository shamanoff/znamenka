package ru.znamenka.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.util.Assert.notNull;

/**
 * <p>
 *     Реализация {@link ExecutorQueries}
 * </p>
 * Создан 21.06.2016
 * <p>
 * Изменения:
 * <p>
 * 29.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>далил аннотацию {@link org.springframework.data.repository.NoRepositoryBean}</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
@Repository("mainExecutor")
public class ExecutorQueriesImpl implements ExecutorQueries {

    /**
     * Менеджер для упраления бизнес-моделями
     */
    private final EntityManager entityManager;

    public ExecutorQueriesImpl(@Autowired EntityManager em) {
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
    public <T> T executeQueryOne(@NotNull JPAQuery<T> query) {
        notNull(query);
        return query.fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> executeQueryList(@NotNull JPAQuery<T> query) {
        notNull(query);
        return query.fetch();
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
            content = this.executeQueryList(query);
        } else {
            content = emptyList();
        }

        return new PageImpl<>(content, pageable, total);
    }

}
