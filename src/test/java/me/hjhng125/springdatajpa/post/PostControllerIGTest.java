package me.hjhng125.springdatajpa.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIGTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostJpaRepository postJpaRepository;

    @Test
    void getPost() throws Exception {
        Post post = new Post();
        post.setTitle("spring data jpa");
        Post save = postJpaRepository.save(post);

        mockMvc.perform(get("/posts/" + save.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(save.getTitle()));
    }

//    @Test
//    void getPosts() throws Exception {
//        Post post = new Post();
//        post.setTitle("spring data jpa");
//        postJpaRepository.save(post);
//
//        mockMvc.perform(get("/posts")
//            .param("page", "0")
//            .param("size", "10")
//            .param("sort", "createdAt,desc")
//            .param("sort", "title"))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.content[0].title", is("spring data jpa")));
//    }

    @Test
    void getPostsWithHateoas() throws Exception {
        createPost();

        mockMvc.perform(get("/posts")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "createdAt,desc")
            .param("sort", "title"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    private void createPost() {
        int postsCnt = 100;

        while(postsCnt > 0) {
            Post post = new Post();
            post.setTitle("spring data jpa");
            postJpaRepository.save(post);
            postsCnt--;
        }

    }
}
