package me.hjhng125.springdatajpa.comment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

// 사용할 기능을 직접 정의하고 싶을때 (JpaRepository에 정의된 메소드를 사용하고 싶지 않은 경우)
@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentJpaRepository {

    // 메서드의 기능을 Spring Data Jpa가 구현할 수 있는 것이라면 구현하여 제공해준다.
    Comment save(@NonNull Comment comment);

    List<Comment> findAll();

    @Nullable
    Optional<Comment> findById(@NonNull Long id);
}
