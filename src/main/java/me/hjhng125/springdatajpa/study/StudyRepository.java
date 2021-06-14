package me.hjhng125.springdatajpa.study;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// 각 저장소마다 쿼리를 생성하는 방법이 다르다. ex) spring-data-redis, spring-data-jpa 등등
public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findByNameStartsWith(String name);

//    @Query("SELECT s FROM Study s WHERE s.name = ?1") // 만약 nativeQuery라면 nativeQuery = true를 준다.
//    List<Study> findByName(String name);

//    쿼리 뒤에 order by를 추가하지 않아도 sort만 추가해도 정렬할 수 있다.
//    정렬 옵션에 들어갈 수 있는 값은 property이거나, alias어야 한다.
//    @Query("SELECT s FROM Study s WHERE s.name = ?1")
//    @Query("SELECT s, s.name AS sName FROM Study s WHERE s.name = ?1") // 이처럼 alias가 적용된 sName도 가능하다.
//    List<Study> findByName(String name, Sort sort);

//    @Query("SELECT s FROM Study as s WHERE s.name = :name")
    @Query("SELECT s FROM #{#entityName} as s WHERE s.name = :name") // SPEL을 사용하면 @Query에서 엔터티 이름을 #{#entityName}로 표현할 수 있다. 자동으로 매핑됨
    List<Study> findByName(@Param("name") String keyword, Sort sort);


}
