package me.hjhng125.springdatajpa.study;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// 각 저장소마다 쿼리를 생성하는 방법이 다르다. ex) spring-data-redis, spring-data-jpa 등등
public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findByNameStartsWith(String name);

    @Query("SELECT s FROM Study s WHERE s.name = ?1") // 만약 nativeQuery라면 nativeQuery = true를 준다.
    List<Study> findByName(String name);
}
