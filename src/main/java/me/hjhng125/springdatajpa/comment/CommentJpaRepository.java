package me.hjhng125.springdatajpa.comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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

//    Comment findById(@NonNull Long id);

    // spring data jpa가 쿼리 만듬.
    // ORM의 구현체마다 구현이나 동작하는 방식이 다르지만 spring data jpa는 기본적으로 Hibernate를 사용하기 때문에 HQL이 기본으로 받아드림
    // native쿼리를 작성하고 싶다면 nativeQuery옵션을 true로 줘야한다.
    @Query(value = "SELECT c FROM Comment AS c")
    List<Comment> findByTitleContains(String keyword);

    List<Comment> findByCommentContainsIgnoreCase(String keyword);

    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int count);

    List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountAsc(String keyword);

    Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    Stream<Comment> findByCommentContains(String keyword);

}
