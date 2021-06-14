package me.hjhng125.springdatajpa.comment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import me.hjhng125.springdatajpa.post.Post;

@Entity
//@NamedEntityGraph(name = "Comment.post", attributeNodes = @NamedAttributeNode("post")) // 이게 복잡하다면?
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String comment;

    private String title;

    private int likeCount = 0;

    /**
     * @ManyToOne의 fetchType의 기본은 EAGER인데 Lazy로 바꾸면 정말 post가 필요할 때만 조회하게 할 수 있다.
     * 하지만 이러한 행위는 post를 조회하는 모든 쿼리에 적용되게 되는데
     * 기본적으로 선언된 fetch전략을 사용하지만 특정 쿼리에 연관 관계릐 Fetch 모드를 설정할 수 있다. -> EntityGraph
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
