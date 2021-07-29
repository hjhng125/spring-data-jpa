package me.hjhng125.springdatajpa.study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import me.hjhng125.springdatajpa.config.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfiguration.class)
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

//        List<Study> studies = studyRepository.findByName("spring data jpa");
//        List<Study> studies = studyRepository.findByName("spring data jpa", Sort.by(Direction.DESC, "name")); // 정렬 옵션에 들어갈 수 있는 값은 property이거나, (name)
//        List<Study> studies = studyRepository.findByName("spring data jpa", Sort.by(Direction.DESC, "sName")); // alias어야 한다 (sName).
        List<Study> studies = studyRepository.findByName("spring data jpa", JpaSort.unsafe("LENGTH(name)")); // 위의 두 경우가 아닌 함수를 사용하고 싶다면 JpaSort.unsafe를 사용하자.

        assertThat(studies.size()).isEqualTo(1);
    }

    @Test
    void updateStudy() {
        Study spring = createStudy("spring data jpa");

        int update = studyRepository.updateName("hibernate", spring.getId());
        assertThat(update).isEqualTo(1);

        /**
         * 위에서 업데이트를 했고 쿼리도 정상적으로 발생했지만 아래의 테스트는 실패한다.
         * 왜냐하면 아직 트랜잭션이 끝나지 않았기에 select query가 발생하지 않고,
         * 캐시에 남아있는 'spring data jpa'라는 이름의 객체를 그대로 리턴했기 때문이다.
         *
         * 위의 업데이트는 사실상 persistenceContext에서 관리하는 객체를 수정하지 않고 DB의 데이터를 바로 수정했다.
         * 따라서 DB에 쿼리를 날려야만 반영된 것을 알 수 있기에 지금 이 트랜잭션에서는 변경되었음을 알 수가 없다.
         *
         * 이 방법을 해결하기 위해서 repository에 명시한 @Modifying에 clearAutomatically = true를 주는 것이 하나의 방법이 될 것이다.
         * 쿼리 실행 후 persistenceContext를 clear해주면 select쿼리가 왔을 때 persistenceContext가 비워져있으니 새로 DB에서 읽어온다.
         * */
        Optional<Study> studyOptional = studyRepository.findById(spring.getId());
        assertThat(studyOptional.get().getName()).isEqualTo("hibernate");
    }

    @Test
    void updateStudy_jpa() {
        Study study = createStudy("spring data jpa");
        study.setName("hibernate");

        assertThat(study.getName()).isEqualTo(studyRepository.findById(study.getId()).get().getName());
    }

    @Test
    void selectTest() {
        //given
        Study test = createStudy("test");
        //when
        List<Study> all = studyRepository.findAll();
        //then
        for (Study study : all) {
            System.out.println("study = " + study);
        }
        assertThat(all).extracting("name").contains("test");
    }
}