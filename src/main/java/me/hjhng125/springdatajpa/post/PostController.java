package me.hjhng125.springdatajpa.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostJpaRepository postJpaRepository;

    public PostController(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    /**
     * Converter란 하나의 타입을 다른 타입으로 변환하는 interface
     * DomainConverter는 자동으로 ConverterRegistry 들어감.
     * ConverterRegistry에 들어가면 Spring MVC에서 데이터를 바인딩 받아서 변환할 때 참고함.
     * ((ConverterRegistry)this.conversionService).addConverter(this);
     *<br/>
     * DomainClassConverter를 적용하지 않으면 아래와 같이 코딩해야 한다.
     * @GetMapping("/posts/{id}")
     * public String getPost(@PathVariable Long id) {
     *    Optional<Post> post = postJpaRepository.findById(id);
     *    return post.get().getTitle();
     * }
     *<br/>
     * DomainClassConverter를 적용하면 아래와 같이 수정할 수 있다.
     * 파라미터를 엔터티 클래스로 수정하고,
     * 아래처럼 파라미터와 PathVariable 이름이 같지 않기 때문에 명시해준다.
     *<br/>
     * DomainClassConverter의 ToEntityConverter의 convert는
     * return id == null ? null : invoker.invokeFindById(id).orElse((Object)null);
     * 위의 코드처럼 findById를 실행시켜 id를 통해 자동으로 Entity로 변환해준다.
     *<br/>
     * Formatter는 Converter와 관심사가 다르다.
     * Formatter는 문자열 기반으로, 문자열을 다른 타입으로 바꾸며,
     * 다른 타입을 문자열로 바꾸기 위한 interface
     * Formatter 또한 Spring MVC에서 데이터를 바인딩 받아서 변환할 때 참고함.
     * 하지만 아래 케이스는 Long타입을 Entity로 변환하기 때문에 Formatter는 사용할 수 없음.
     * */
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") Post post) {
        return post.getTitle();
    }

    /**
     * Spring MVC는 handler 메서드 파라미터들에 아래와 같은 타입을 지원한다.
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodArgumentResolver.html
     * 추가로 Spring data jpa의 웹 기능을 사용하면 Pageable과 Sort를 지원한다.
     * */
//    @GetMapping("/posts")
//    public Page<Post> getPosts(Pageable pageable) {
//        return postJpaRepository.findAll(pageable);
//    }

    /**
     * HATEOAS를 쓰면 Pageable에 들어있는 값을 통해
     * 클라이언트에서 연관된 링크를 유추할 필요가 없다.
     *
     * spring hateoas 1.2.4에서 아래와 같이 변경되었다.
     *
     * ResourceSupport is now RepresentationModel
     * Resource is now EntityModel
     * Resources is now CollectionModel
     * PagedResources is now PagedModel
     * */
    @GetMapping("/posts")
    public PagedModel<EntityModel<Post>> getPostsWithHateoas(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toModel(postJpaRepository.findAll(pageable));
    }
}
