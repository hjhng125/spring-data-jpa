package me.hjhng125.springdatajpa.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 기본적으로 JpaRepository는 트랜잭션이 적용되어 있다.
 * JpaRepository의 구현체인 SimpleJpaRepository에 이미 클래스레벨에 @Transactional(readOnly = true) 이 적용되어 있음.
 * <br/>
 * 메서드에 가장 가까운 설정이 우선시된다.
 *<br/>
 * SimpleJpaRepository를 살펴보면
 * delete~, save~ 와 같은 메서드들은 @Transactional 이라고 메서드레벨에 정의되어 있으며,
 * find~와 같은 조회 메서드에는 정의되어 있지 않다.
 * 이런 경우 @Transactional(readOnly = true)가 적용된다.
 *<br/>
 * Transactional이 정의되어 있는 메서드 내부에 다른 트랜잭션이 적용된 메서드 여러개가 들어있다면
 * 가장 상위 메서드의 트랜잭션이 적용된다.
 *<br/>
 * 이 어노테이션이 적용되어 있는 메서드에서 RuntimeException 이나 Error가 발생하면 Rollback을 시킨다.
 * CheckedException는 롤백을 안함.
 *<br/>
 * CheckedException이 발생했을때에 롤백하고 싶다면 rollbackFor, rollbackForClassName 옵션을 주어야하며,
 * RuntimeException이나 Error에서 롤백하고 싶지 않다면 noRollbackFor, noRollbackForClassName 옵션을 주어야 함.
 *<br/>
 * timeout을 설정할 수도 있으며, transactionManager를 설정할 수도 있다.
 * transactionManager는 기본적으로 jpaTransactionManager이다.
 * transactionManager를 여러개 설정하지 않는 이상 설정할 일은 없을 것이다.
 * spring boot를 쓰며 transactionManager가 하나일 경우 스프링 자동 설정에서 설정한 transactionManager Bean name이 기본값으로 설정된다.
 *<br/>
 * readOnly는 해당 트랜잭션이 읽기전용인지 아닌 나타내는 옵션이다.
 * 이 값을 통해 성능 최적화를 해줄 수 있는 여지가 생긴다.
 * readOnly는 flush모드를 MANUAL로 설정해준다.
 * FlushModeType.MANUAL은 commit()이 발생해도 명시적으로 session.flush()를 호출하지 않는한 flush가 실행되지 않는다.
 * 따라서 해당 트랜잭션 내에서는 entity 객체와 snapshot을 비교하는 dirty checking을 하지 않는다.
 * 데이터를 변경하는 operation이 없다면 readOnly를 true로 주는 것이 좋다.
 *<br/>
 * isolation : 여러개의 트랜잭션이 동시에 DB에 접근했을 때 이 트랜잭션들을 어떻게 처리할 것인가
 *             제한적이게 될 수록 성능은 안좋아진다.
 *         default : 데이터베이스의 기본값을 따른다.
 *<br/>
 * propagation : 트랜잭션을 어떻게 전파할 것인가.
 *               nested 트랜잭션에 대한 트랜잭션 전파옵션.
 *         default : REQUIRED
 *<br/>
 * isolation과 propagation은 blog에서 더 깊게 작성할 예정이다.
 */
//@Transactional
public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board>, QueryByExampleExecutor<Board> {


}
