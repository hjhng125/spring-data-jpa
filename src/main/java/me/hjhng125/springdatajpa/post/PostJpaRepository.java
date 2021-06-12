package me.hjhng125.springdatajpa.post;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {

    Page<Post> findByTitleContains(String title, Pageable pageable);

    long countByTitleContains(String title);

    @Async
    ListenableFuture<List<Post>> findByTitleContainsIgnoreCase(String keyword);
}
