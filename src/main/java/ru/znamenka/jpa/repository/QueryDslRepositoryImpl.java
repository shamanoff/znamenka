package ru.znamenka.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import ru.znamenka.jpa.model.BaseModel;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 *     Реализация для метода {@link QueryDslRepository#getJavaType()}
 * <p>
 * Создан 21.06.2016
 * <p>
 * Изменения:
 * <p>
 * 27.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>
 *         Переопределен метод {@link org.springframework.data.repository.CrudRepository#save(Object)}
 *         Убрана возможность update бизнес модели
 *     </li>
 *     <li>Добавлена реализация {@link QueryDslRepository#update(Object)}</li>
 * </ul>
 * 29.06.2016 - Евгений Уткин (Eugene Utkin)
 * <ul>
 *     <li>Удалил аннотацию {@link org.springframework.data.repository.NoRepositoryBean}</li>
 * </ul>
 * @author Евгений Уткин (Eugene Utkin)
 */
public class QueryDslRepositoryImpl<T extends BaseModel<ID>, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements QueryDslRepository<T, ID> {

    private final JpaEntityInformation<T, ID> entityInformation;

    private final EntityManager em;

    /**
     * {@inheritDoc}
     */
    public QueryDslRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    public QueryDslRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {
        super(entityInformation, entityManager, resolver);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<T> getJavaType() {
        return entityInformation.getJavaType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        notNull(entity, "Target object must not be null");
        if (entity.getId() != null && this.exists(entity.getId())) {
            throw new EntityExistsException(entity.getId().toString());
        }
        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        }
        return em.merge(entity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public <S extends T> S update(S entity) {
        if (!this.exists(entity.getId())) {
            throw new EntityNotFoundException(entity.getId().toString());
        }
        return em.merge(entity);

    }
}
