package me.hjhng125.springdatajpa.comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
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


    /**
     * EntityGraphType
     * FETCH : 기본 타입과 설정한 관계에 대해 Eager모드로 가져온다. 설정되지 않은 관계는 Lazy
     * LOAD : 기본 타입과 설정한 관계에 대해 Eager모드로 가져온다. 설정되지 않은 관계는 기본 패치 전략 따름(~one은 eager, ~many는 lazy) */
//    @EntityGraph(value = "Comment.post", type = EntityGraphType.FETCH)
    @EntityGraph(attributePaths = "post") // 위와 같음. 더 간편함. 하지만 이것이 중복이 된다면 전처럼 entity위에 namedEntityGraph 선언 해야함
    Optional<Comment> getById(Long id);

}
