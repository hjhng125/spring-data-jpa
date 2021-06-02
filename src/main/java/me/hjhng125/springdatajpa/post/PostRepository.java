package me.hjhng125.springdatajpa.post;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// JPARepository를 사용하지 않고 작성한 Repository 예제
@Repository
@Transactional
public class PostRepository {

    // Autowired를 사용할 수 있지만 JPA 어노테이션을 사용하여
    // 스프링 코드를 최대한 감추며 의존성 주입을 받음.
    // (스프링의 비침투성: 스프링 프레임워크의 최대한 코드를 노출하지 않는다.)
    @PersistenceContext
    EntityManager entityManager;

    public Post add(Post post) {
        entityManager.persist(post);
        return post;
    }

    public void delete(Post post) {
        entityManager.remove(post);
    }

    public List<Post> finaAll() {
        return entityManager.createQuery("SELECT p FROM Post as p", Post.class).getResultList();
    }

}
