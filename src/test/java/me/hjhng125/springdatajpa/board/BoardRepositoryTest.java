package me.hjhng125.springdatajpa.board;

import static me.hjhng125.springdatajpa.board.BoardSpecs.isBest;
import static me.hjhng125.springdatajpa.board.BoardSpecs.isTitleTop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    /**
     * querydsl의 Predicate와 비슷한 기능이며, querydsl의 Predicate와 같이 사용할 수 있다. Spec을 원하는 이름으로 정의함에 따라 가독성을 늘릴 수 있으며, 조건에 대한 코드 계층을 나눔으로써 클라이언트쪽 코드가 깔끔해진다.
     * <p>
     * 단, 여러가지 조합을 하는 경우 테스트를 철저히 해야한다.
     */
    @Test
    void specsTest() {
        boardRepository.findAll(isBest().and(isTitleTop()),
            PageRequest.of(0, 20));
    }

    /**
     * Query By Example
     * 예제 객체를 통해 쿼리를 만드는 방법
     *
     * Example = Probe + ExampleMatcher
     *
     * Probe : 도메인 객체
     * ExampleMatcher : Probe에 들어있는 필드의 값들을 `실제 DB와 어떻게 매칭할 것인가`를 정의한 것
     *
     * 기본적으로 Probe와 완전히 동일한 것만들 가져옴
    * */
    @Test
    void qbe() {
        Board probe = new Board();
        probe.setBest(true);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
            .withIgnorePaths("title", "description");

        Example<Board> example = Example.of(probe, exampleMatcher);
        boardRepository.findAll(example);
    }

}