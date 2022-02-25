package com.communardo.blogApp.initialize;

import com.communardo.blogApp.controller.BlogPostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BlogPostApplicationContextTests {

    @Autowired
    private BlogPostController blogPostController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void contextCreatesController() {
        assertThat(blogPostController).isNotNull();
    }
}
