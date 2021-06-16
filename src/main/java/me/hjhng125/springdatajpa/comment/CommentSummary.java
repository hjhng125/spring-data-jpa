package me.hjhng125.springdatajpa.comment;

import org.springframework.beans.factory.annotation.Value;

public interface CommentSummary {

    /**
     * 아래 세개(comment, up, down)처럼 지정한 방식은 애초에 select할 때 딱 세개만 조회한다
     * closed projection은 쿼리가 좀더 효율적이다.
     * */
    String getComment();

    int getUp();

    int getDown();

    /**
     * 이 방식은 결국 target이 뭔지 알아야 하기에 Comment의 모든 컬럼을 모두 가져온 후 @Value(SPEL)를 사용하여 연산할 수 있다.
     * open projection은 쿼리의 효율은 향상시킬 수 없지만 마치 sql의 alias와 같이 자유로운 표현이 가능하다. spring bean의 메소드도 호출할 수 있다.
     * */
//    @Value("#{target.up + ' ' + target.down}")
//    String getVotes();

    /**
     * open projection의 장점을 살린 closed projection도 가능하다.
     * java8부터 default 메소드를 interface에 사용할 수 있게 되어
     * 아래와 같이 커스텀한 메소드를 사용하여 우리가 사용할 필드를 한정할 수 있다.
     * 이렇게 쿼리의 성능을 높일 수 있고, 커스텀하게 무언가를 계산해 낼수도 있다.
     * 이러한 방법은 클래스로도 가능하다.
     * 클래스의 프로퍼티로 사용할 필드를 선언하고 AllArgsConstructor, getter를 구현하고 커스텀한 메소드를 통해 아래와 같이 표현할 수 있다.
     * */
    default String getVotes() {
        return getUp() + " " + getDown();
    }
}
