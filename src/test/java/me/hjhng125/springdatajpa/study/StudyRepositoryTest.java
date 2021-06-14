package me.hjhng125.springdatajpa.study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import me.hjhng125.springdatajpa.config.StudyListenerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(StudyListenerConfig.class)
class StudyRepositoryTest {

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 아래와 같은 방식으로 직접 event를 정의하고
     * 어플리케이션 컨텍스트를 통해 이벤트 퍼블리싱할 수 있다.
     * 하지만 spring data는 save()가 호출될 때, 이벤트 자동 퍼블리싱을 지원한다.
     * @DomainEvents가 선언된 메소드에 이벤트를 모아두고
     * @AfterDomainEventPublication가 선언된 메소드에서 Collections에 쌓여있던 이벤트를 자동으로 비워준다. */
    @Test
    void event() {
        Study study = createStudy("spring data jpa");

        StudyEvent studyEvent = new StudyEvent(study);
        applicationContext.publishEvent(studyEvent);
    }

    /**
     * 이처럼 이벤트를 만들기만 하면
     * save()가 호출될 때 자동으로 퍼블리싱한다.*/
    @Test
    void domain_event() {
        createStudy("spring data jpa");
    }

    private Study createStudy(String name) {
        Study study = new Study();
        study.setName(name);
        return studyRepository.save(study.registerEvent());
    }

    @Test
    void findByNameStartsWith() {
        createStudy("spring data jpa");

        List<Study> studies = studyRepository.findByNameStartsWith("spring");
        assertThat(studies.size()).isEqualTo(1);

    }

    @Test
    void findByNameWithNamedQuery() {
        createStudy("spring data jpa");

        List<Study> studies = studyRepository.findByName("spring data jpa");
        assertThat(studies.size()).isEqualTo(1);
    }

}