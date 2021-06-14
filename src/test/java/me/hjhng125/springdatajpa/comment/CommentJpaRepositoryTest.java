package me.hjhng125.springdatajpa.comment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import me.hjhng125.springdatajpa.post.Post;
import me.hjhng125.springdatajpa.post.PostJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CommentJpaRepositoryTest {

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Test
    void save() {

        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        Comment save = commentJpaRepository.save(comment);

        List<Comment> comments = commentJpaRepository.findAll();
        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    void findById_with_optional() throws IllegalArgumentException{
        Optional<Comment> byId = commentJpaRepository.findById(100L);

        Comment comment = byId.orElseThrow(IllegalArgumentException::new);

    }

    @Test
    void findById() {
//        Comment comment = commentJpaRepository.findById(100L);
//        if (comment == null) {
//            throw new IllegalArgumentException();
//        }
    }

    @Test
    void findByCommentContainsIgnoreCase() {
        Comment comment = new Comment();
        comment.setComment("spring data jpa");
        commentJpaRepository.save(comment);

        List<Comment> comments = commentJpaRepository.findByCommentContainsIgnoreCase("Spring");

        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    void findByCommentContainsIgnoreCaseAndLikeCountGreaterThan() {
        Comment comment = new Comment();
        comment.setComment("spring data jpa");
        comment.setLikeCount(11);
        commentJpaRepository.save(comment);

        List<Comment> comments = commentJpaRepository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThan("Spring", 10);

        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    void findByCommentContainsIgnoreCaseAndOrderByLikeCount() {
        this.createComment("spring", 10);
        this.createComment("jpa", 30);
        this.createComment("data", 20);

        List<Comment> comments = commentJpaRepository.findByCommentContainsIgnoreCaseOrderByLikeCountAsc("Spring");

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 10);
    }

    private void createComment(String comment, int likeCount) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setLikeCount(likeCount);
        commentJpaRepository.save(newComment);
    }

    @Test
    void findByCommentContainsIgnoreCaseWithPageable() {
        this.createComment("spring", 10);
        this.createComment("jpa", 30);
        this.createComment("data", 20);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Direction.DESC, "likeCount"));

        Page<Comment> commentPage = commentJpaRepository.findByCommentContainsIgnoreCase("SPRING", pageRequest);

        assertThat(commentPage.getTotalElements()).isEqualTo(1);
        assertThat(commentPage.getTotalPages()).isEqualTo(1);
        assertThat(commentPage.getNumberOfElements()).isEqualTo(1);
    }

    @Test
    void findByCommentContainsIgnoreCaseToStream() {
        this.createComment("spring", 10);
        this.createComment("jpa", 30);
        this.createComment("data", 20);

        // Stream - java8부터 들어옴
        try(Stream<Comment> commentStream = commentJpaRepository.findByCommentContains("spring")) {
            Optional<Comment> first = commentStream.findFirst();
            Comment comment = first.orElseThrow(IllegalArgumentException::new);
            assertThat(comment.getLikeCount()).isEqualTo(10);
        }

    }

    @Test
    void getComment() {
        Post post = new Post();
        post.setTitle("spring");
        Post savedPost = postJpaRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("spring data jpa");
        comment.setPost(savedPost);
        Comment savedComment = commentJpaRepository.save(comment);

        /**
         * Post left join 해서 가져옴.
         * @~One은 fetchType이 기본값이 Eager이기 때문
         * @~Many는 fetchType이 기본값이 Lazy임
         * */

        Optional<Comment> getById = commentJpaRepository.getById(1L);
        System.out.println("============================================");
        Optional<Comment> byId = commentJpaRepository.findById(1L);
    }

    @Test
    void findByPost_Id() {
//        List<Comment> byPost_id = commentJpaRepository.findByPost_Id(1L);// 모든 컬럼 다가져옴
        List<CommentSummary> byPost_id = commentJpaRepository.findByPost_Id(1L, CommentSummary.class); // CommentSummary에 선언된 프로퍼티만 가져옴 - Closed Projection

        Post post = new Post();
        post.setTitle("spring");
        Post savePost = postJpaRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("spring data jpa");
        comment.setPost(savePost);
        comment.setUp(10);
        comment.setDown(1);
        Comment save = commentJpaRepository.save(comment);

        commentJpaRepository.findByPost_Id(savePost.getId(), CommentSummary.class).forEach(c -> {
            System.out.println("========================================");
            System.out.println(c.getVotes());
        });

        commentJpaRepository.findByPost_Id(savePost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("========================================");
            System.out.println(c.getComment());
        });
    }
}