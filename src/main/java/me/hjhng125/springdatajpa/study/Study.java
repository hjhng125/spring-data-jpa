package me.hjhng125.springdatajpa.study;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import me.hjhng125.springdatajpa.account.Account;

@Entity
public class Study {

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
}
