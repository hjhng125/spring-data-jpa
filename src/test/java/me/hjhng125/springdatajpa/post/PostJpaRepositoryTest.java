package me.hjhng125.springdatajpa.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@DataJpaTest
@ActiveProfiles("test")
class PostJpaRepositoryTest {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void CRUDRepository() {
        // Given
        Post post = new Post();
        post.setTitle("spring boot common");
        assertThat(post.getId()).isNull();

        // When
        Post newPost = postJpaRepository.save(post);

        // Then
        assertThat(newPost.getId()).isNotNull();

        // When
        List<Post> all = postJpaRepository.findAll();

        // Then
        assertThat(all.size()).isEqualTo(1L);
        assertThat(all).contains(newPost);

        // When
        Page<Post> page = postJpaRepository.findAll(PageRequest.of(0, 10));

        // Then
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // When
        Page<Post> spring = postJpaRepository.findByTitleContains("spring", PageRequest.of(0, 10));

        // Then
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        // When
        long count = postJpaRepository.countByTitleContains("spring");

        // Then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    void findByTitleContainsIgnoreCaseAsync() throws ExecutionException, InterruptedException {
        this.createPost("Spring");
        this.createPost("data");
        this.createPost("jpa");
        ListenableFuture<List<Post>> future = postJpaRepository.findByTitleContainsIgnoreCase("spring");
//        System.out.println("=======================");
//        System.out.println("Is Done? " + future.isDone());
//        List<Post> posts = future.get();
        future.addCallback(new ListenableFutureCallback<List<Post>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(@Nullable List<Post> posts) {
                Objects.requireNonNull(posts).forEach(System.out::println);
            }
        });

        // 이 방법은 main class에 @EnableAsync를 붙여야 비동기로 실행되게 할 수 있다.
        // 하지만 이 방법은 테스트를 하기 어렵기에 더욱 고민이 필요하다.
    }

    private void createPost(String title) {
        Post post = new Post();
        post.setTitle(title);
        postJpaRepository.save(post);
    }

    @Test
    void findMyPost() {
        this.createPost("spring");
        this.createPost("data");
        this.createPost("jpa");
        List<Post> myPost = postJpaRepository.findMyPost();

        assertThat(myPost.size()).isEqualTo(3);
    }

    @Test
    void save() {
        Post post = new Post();
        post.setTitle("hibernate");
        postJpaRepository.save(post);
        // test에서의 save()는 insert쿼리를 발생하지 않는다.
        // @DataJpaTest 어노테이션 내부엔 @Transactional 어노테이션이 포함되어 있으며
        // 이는 테스트 메서드가 실행될때 트랜잭션을 시작하고, 메서드가 종료되면 트랜잭션을 롤백해준다.
        // Hibernate는 한 트랜잭션 내에서 불필요한 쿼리를 발생시키지 않는데,
        // @Transactional 어노테이션이 메소드가 끝날때 롤백할 것이기 때문에 Hibernate가 불필요한 쿼리라 판단하는 것으로 보인다.
    }

    @Test
    void delete() {
        Post post = new Post();
        post.setTitle("hibernate");
        postJpaRepository.save(post);

        postJpaRepository.delete(post);

        // insert, delete 쿼리 둘다 발생하지 않았다.
        // 어차피 롤백할 것이기 때문에 둘다 발생하지 않음.
    }

    @Test
    void save_with_select() {
        Post post = new Post();
        post.setTitle("hibernate");
        postJpaRepository.save(post);

        postJpaRepository.findAll();

        postJpaRepository.delete(post);

        // save를 하여 영속성 컨텍스트에 객체가 들어있는데,
        // findAll()을 호출하여 기존의 DB에 존재하는 값을 조회하려 할 경우 저 save() 메소드는 다음 select문에 영향을 미친다.
        // 따라서 insert 쿼리와 select 쿼리가 발생한 것이며
        // delete의 경우 어차피 롤백할 것이기에 발생하지 않음.
    }

    @Test
    void delete_with_flush() {
        Post post = new Post();
        post.setTitle("hibernate");
        postJpaRepository.save(post);

        postJpaRepository.delete(post);
        postJpaRepository.flush();

        // flush()를 통해 강제적으로 쿼리 발생시킴
    }

    @Test
    void save_and_update() {

        // 이 과정은 Transient한 상태의 객체를 Persistent한 객체로 만드는 것
        // save() 내부적으로 PersistenceContext에 객체가 없다면 persist()를 호출한다.
        // entity의 @Id가 명시된 프로퍼티를 찾고, 해당 프로퍼티가 null이면 Transient 상태로 판단한다.
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postJpaRepository.save(post);// insert, 리턴된 객체는 항상 PersistenceContext에 영속화되어 있는 객체이다.

        assertTrue(entityManager.contains(post));
        assertTrue(entityManager.contains(save));
        assertSame(save, post);

        // 이 과정은 Detached한 상태의 객체를 Persistent한 객체로 만드는 것
        // save() 내부적으로 PersistenceContext에 객체가 이미 존재하기에 merge()를 호출한다.
        // entity의 @Id가 명시된 프로퍼티를 찾고, 해당 프로퍼티가 존재하면 Detached 상태로 판단한다.
        Post newPost = new Post();
        newPost.setId(post.getId());
        newPost.setTitle("spring data jpa");
        Post merge = postJpaRepository.save(newPost);// update
        // 리턴된 merge는 PersistenceContext에서 merge후 관리하고 있는 상태의 객체이다.
        // 하지만 newPost는 PersistenceContext에 캐싱된 객체가 아니다.
        // merge는 내부적으로 파라미터로 전달받은 객체의 복사본을 만들고 이 복사본을 영속화시킨 후 리턴한다.
        // 따라서 아래와 같은 결과를 얻을 수 있다.

        assertTrue(entityManager.contains(merge));
        assertThat(entityManager.contains(newPost)).isFalse();
        assertThat(newPost == merge).isFalse();

        //

        List<Post> all = postJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}