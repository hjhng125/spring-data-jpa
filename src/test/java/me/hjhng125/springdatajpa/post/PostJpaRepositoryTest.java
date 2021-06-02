package me.hjhng125.springdatajpa.post;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
}