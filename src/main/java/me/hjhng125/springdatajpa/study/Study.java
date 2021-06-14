package me.hjhng125.springdatajpa.study;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import me.hjhng125.springdatajpa.account.Account;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
// 이런식의 Named Query가 많아지면 도메인 클래스가 더러워지고 jpql 이나 sql 코드가 있는 것이 보기 좋지 않다.
//@NamedQuery(name = "Study.findByName", query = "SELECT s FROM Study s WHERE s.name = ?1") // jpql
//@NamedNativeQuery() 를 통해 native query도 가능하다.
public class Study extends AbstractAggregateRoot<Study> {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne // 현재 reference가 한개이므로 one이라고 생각하자.
    // 자기 자신안에 해당 entity에 대한 외래키를 생성한다.
    private Account owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Study registerEvent() {
        this.registerEvent(new StudyEvent(this));
        return this;
    }
}
