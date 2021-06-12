package me.hjhng125.springdatajpa.common;


import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * SimpleJpaRepository는 JpaRepository의 최하단의 가장 많은 기능을 담고 있는 구현체이다.
 * 이를 확장하고 우리가 정의한 인터페이스를 구현한다.
 * 이 클래스는 baseRepository로 등록되어야 한다.
 * @EnableJpaRepositories(repositoryBaseClass = SimpleRepositoryImpl.class)
 * */
public class SimpleRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements SimpleRepository<T, ID> {

    private final EntityManager entityManager;

    public SimpleRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }

    // JpaRepository에 구현되어 있는 다른 메소드를 재정의 할 수 있다.
    @Override
    public void deleteAll() {
        super.deleteAll();
    }
}
