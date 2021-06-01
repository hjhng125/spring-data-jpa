package me.hjhng125.springdatajpa.account;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import me.hjhng125.springdatajpa.address.Address;
import me.hjhng125.springdatajpa.study.Study;
import org.springframework.data.annotation.CreatedDate;

// 테이블과 도메인 객체를 어떻게 매핑시킬지 hibernate에 알려야한다. ex) annotation or XML
@Entity(name = "myAccount") // 해당 annotation을 통해 테이블 컬럼과 객체의 properties가 매핑된다.
// Entity의 이름은 보통 클래스 이름과 같게 하지만 값을 변경하면 Hibernate 내부에선 Entity의 이름이 변경된다.
// 즉 Jpa 안에서, 객체의 세상에서의 이름이 변경된 것이다.
// @Table - @Table의 Default는 Entity의 name과 같다. relation 세상에서의 이름
// Entity의 name을 지정하지 않으면 기본적으로 클래스 이름을 사용하기 때문에 클래스의 이름이 곧 Table의 이름이 된다. 이러면 생략 가능
public class Account { // Entity Type은 @Id(식별자)를 갖고 있다.

    // value type -> properties (Entity type에 종속적이다.)
    @Id // primary key mapping 자바의 primitive, wrapper 타입과 String, Date, BigDecimal, BigInteger 타입 가능
    @GeneratedValue // 값 자동 생성 Default는 DB에 따라 생성 전략이 다르다(GenerationType.AUTO).
    // Postgres는 SEQUENCE가 기본
    // IDENTITY : 기본 키 생성을 DB에 위임. db의 auto_increment 동작이 수행됨.
    // SEQUENCE : DB 시퀀스를 사용해 기본 키 할당. DB Sequence object 사용.
    //            db 시퀀스는 유일한 값을 순서대로 생성하는 db object
    //            @SequenceGenerator에 sequenceName 속성을 추가해야 한다.
    // TABLE : 키 생성 테이블을 사용. 키 생성 전용 테이블을 만들고, 이름과 값으로 사용할 컬럼을 만들어 DB 시퀀스처럼 동작하게 하는 전략
    // AUTO : 데이터베이스 벤더에 의존하지 않고, 벤더에 따라 타입을 지정 ex) oracle은 SEQUENCE.
    private Long id;

    @Column // 생략되어 있는 것과 같다.
    private String name;

    private String password;

    private String email;

    @Temporal(TemporalType.TIMESTAMP) // Calendar, Date에만 붙일 수 있음 ㅜㅜ
    // JPA 2.2부터는 Java8 날짜 시간 모듈 사용 가능함.
    @CreatedDate
    private Date createdAt;

    private LocalDateTime updatedAt;

    @Transient // relation 컬럼과 매핑하지 않음.
    private String no;

    @Embedded // Entity에 종속적인 value임을 명시
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "home_street")) // 새 이름 명명
    })
    private Address address; // Composit한 value type 매핑

//    @OneToMany
//    private Set<Study> studies = new HashSet<>();

    // getter, setter가 없어도 컬럼과 매핑됨.
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

//    public Set<Study> getStudies() {
//        return studies;
//    }
//
//    public void setStudies(Set<Study> studies) {
//        this.studies = studies;
//    }
}
