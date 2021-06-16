package me.hjhng125.springdatajpa.post;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
// 커스텀 리포지토리의 구현 클래스의 접미어는 기본이 'Impl'이다.
// 이것을 변경하기 위해선 @EnableJpaRepositories(repositoryImplementationPostfix = '')를 수정해야 한다.ㅏ
public class PostCustomRepositoryImpl implements PostCustomRepository<Post>{

    private final EntityManager entityManager;

    public PostCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> findMyPost() {
        System.out.println("findMyPost");
        return entityManager.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }

    @Override
    public void delete(Post post) {
        System.out.println("custom delete");
        entityManager.remove(post);
    }
}
