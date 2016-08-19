package ru.znamenka.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import ru.znamenka.jpa.model.BaseModel;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * <p>
 *     Реализация для метода {@link QueryDslRepository#getJavaType()}
 * <p>
 * Создан 21.06.2016
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

}
