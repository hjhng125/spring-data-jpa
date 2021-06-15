package me.hjhng125.springdatajpa.article;

import java.util.List;
import me.hjhng125.springdatajpa.config.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfiguration.class)
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository repository;

    @Test
    void test() {
        Article article = new Article();

        /**
         * insert 쿼리를 확인해보면 audit기능을 통해 이
         * @CreatedDate 어노테이션과 @LastModifiedDate 어노테이션이 선언된 프로퍼티의 값이 잘 매핑된 것을 알 수 있다.
         * 하지만 @CreatedBy, @LastModifiedBy가 선언된 프로퍼티는 null이다.
         *
         * 위의 두 프로퍼티의 값은 Spring Security를 통해 가져와야 한다.
         * 이 프로젝트는 Spring Security를 다루지 않고 본인 또한 아직 Security에 대한 견문이 좁기에
         * 다음 기회에 Security까지 포함한 테스트를 해보도록 할 것이다.
         *
         * 현재는 AuditAware를 빈으로 등록하여 호출이 잘 되는지만 확인해볼 것이다.
         * 이것이 잘 된다면 AuditAware에서 Spring Security의
         * Authentication을 내가 사용하는 도메인 객체로 캐스팅하여 return해주면 될 것이다.
         *
         *
         * 또한 간단한 방법으로 Jpa LifeCycle을 이용하는 방법이 있다.
         * 이는 entity의 변화가 발생했을때 특정한 콜백을 실행할 수 있는 이벤트를 발생시켜준다.
         * auditing 처럼 특별한 설정없이 어노테이션만으로 설정 가능하다.
         * @Pre ~ : 특정 변화 전 호출
         * @Post ~ : 특정 변화 후 호출
         * */
        Article save = repository.save(article);

        List<Article> all = repository.findAll();

    }
}