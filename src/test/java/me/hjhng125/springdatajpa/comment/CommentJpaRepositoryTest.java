package me.hjhng125.springdatajpa.comment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CommentJpaRepositoryTest {

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Test
    void save() {

        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        Comment save = commentJpaRepository.save(comment);

        List<Comment> comments = commentJpaRepository.findAll();
        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Optional<Comment> byId = commentJpaRepository.findById(100L);
        assertThat(byId).isEmpty();
    }
}