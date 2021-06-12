package me.hjhng125.springdatajpa;

import me.hjhng125.springdatajpa.common.SimpleRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// Spring boot가 아닌 Spring을 사용할 경우 아래의 어노테이션을 붙여줘야 JPA를 사용할 수 있다.
// Spring boot가 자동으로 설정해준다.
// @EnableJpaRepositories(repositoryImplementationPostfix = '')
@EnableJpaRepositories(repositoryBaseClass = SimpleRepositoryImpl.class)
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

}
