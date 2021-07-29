# spring data jpa

이 레파지토리에서는 Spring Data JPA에 대하여 공부한 내용의 코드를 포함합니다.

백기선님의 [스프링 데이터 JPA 강좌](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa/dashboard) 를 수강하며 작성한 예제들로 구성되어 있습니다.

자세한 내용은 [블로그](https://hjhng125.github.io/categories/#%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa) 를 확인해주세요.

## 개발환경
* IDE : Intellij IDEA Ultimate
* OS : Mac OS X
* Spring boot 2.4.3
* Java 11
* Maven
* Used Database : postgresql

### Used Database : postgresql
 
##### DB 세팅
```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=hjhng -e POSTGRES_DB=springdata --name postgres -d postgres 
```

##### container 접속
```
docker exec -it postgres bash
```

##### postgres 접속
```
su - postgres
psql --username=hjhng --dbname=springdata
```

##### db list 조회
```
\l or \list
```

##### db table 조회
```
\dt
```

##### psql shell 종료
```
\q
```