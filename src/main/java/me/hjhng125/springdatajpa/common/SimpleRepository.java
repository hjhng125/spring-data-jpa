package me.hjhng125.springdatajpa.common;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 만약 모든 엔터티에 대한 리포지토리에 동일한 기능을 넣고 싶다면 아래와 같이 할 수 있다.
 * 이 인터페이스는 빈으로 등록되면 안되는 중간 단계이기 때문에 @NoRepositoryBean가 필수이다.
 * JpaRepository를 상속받아야 Jpa관련된 기능을 사용할 수 있다.
 * */
@NoRepositoryBean
public interface SimpleRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    boolean contains(T entity);
}
