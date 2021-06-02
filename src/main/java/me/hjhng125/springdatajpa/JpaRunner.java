package me.hjhng125.springdatajpa;

import java.util.List;
import javafx.geometry.Pos;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import me.hjhng125.springdatajpa.post.Post;
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
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Session session = entityManager.unwrap(Session.class);
//
//        Account account = new Account();
//        account.setName("Hong");
//        account.setPassword("hibernate");
//        account.setCreatedAt(new Date());
//        account.setEmail("hjhng125@nate.com");
//
//        Study study = new Study();
//        study.setName("Spring data jpa");
//        study.setOwner(account);
//
//        // 이전까지는 transient 상태로 JPA가 모르는 상태이다.
//        // 이 상태에서 save를 하지 않고 두면 언젠가 GC의 대상이 될 수도 있다.
//
//        session.save(account);
//        session.save(study); // persistent 상태가 된다. (JPA가 존재를 알게됨)
//                             // save를 했다해도 바로 DB에 저장되지 않음(insert query가 바로 발생하지 않고 1차 캐시에만 저장됨)
////        entityManager.persist(account);
//
//        Account hong = session.load(Account.class, account.getId());
//        // 1차 캐시에 저장된 객체를 가져오기 떄문에 select query가 발생하지 않음.
//
//        hong.setName("jinhyung"); // update query 또한 commit이 되고 난 이후 발생한다.
//        System.out.println("========================");
//        System.out.println(hong.getName());
//    } // println 이후에 insert query가 발생한다.
    // query는 transaction이 commit 되고 난 이후(여기선 메서드의 종료 이후)에 발생한다. (write behind)

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Post post = new Post();
//        post.setTitle("Spring Data JPA");
//
//        Comment comment = new Comment();
//        comment.setComment("comment");
//        post.addComment(comment);
//
//        Comment comment1 = new Comment();
//        comment1.setComment("comment1");
//        post.addComment(comment1);
//
//        Session session = entityManager.unwrap(Session.class);
//        session.save(post);
//    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
//
//        List<Post> posts = query.getResultList();
//        posts.forEach(System.out::println);

        // Criteria - Type Safe하나 매우 복잡함.
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
//        Root<Post> from = query.from(Post.class);
//        query.select(from);

//        List<Post> posts = entityManager.createQuery(query).getResultList();
//        posts.forEach(System.out::println);

        // NamedQuery - query를 sql mapper처럼 모아놓고 필요로 하는 곳에서 query name을 호출하는 방식
//        TypedQuery<Post> all_post = entityManager.createNamedQuery("all_post", Post.class);
//        List<Post> posts = all_post.getResultList();
//        posts.forEach(System.out::println);

        // NativeQuery
        List<Post> posts = entityManager.createNativeQuery("SELECT * FROM Post", Post.class).getResultList();
        posts.forEach(System.out::println);

    }
}
