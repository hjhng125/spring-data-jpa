package me.hjhng125.springdatajpa.post;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@DataJpaTest
class PostJpaRepositoryTest {

    @Autowired
    private PostJpaRepository postJpaRepository;

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

}