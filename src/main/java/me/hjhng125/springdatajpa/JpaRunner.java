package me.hjhng125.springdatajpa;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.hjhng125.springdatajpa.account.Account;
import me.hjhng125.springdatajpa.study.Study;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional // 해당 클래스의 모든 메소드에 적용
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager; // jpa의 핵심 (JPA는 Hibernate(ORM Framework)를 사용한다.)

    //    @Transactional // 해당 메소드에만 적용
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setName("Hong");
        account.setPassword("hibernate");
        account.setCreatedAt(new Date());
        account.setEmail("hjhng125@nate.com");

        Study study = new Study();
        study.setName("Spring data jpa");
        study.setOwner(account);
        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);
//        entityManager.persist(account);
    }
}
