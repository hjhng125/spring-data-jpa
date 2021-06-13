package me.hjhng125.springdatajpa.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
}
