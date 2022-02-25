package com.communardo.blogApp.repository;

import com.communardo.blogApp.model.BlogCategory;
import com.communardo.blogApp.model.BlogPost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BlogPostRepositoryTests {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    void setUp() {
        blogPostRepository.save(createBlogPost("TestTitle", "Test blog.", new Date(), "Admin", createBlogCategory(1, "Art")));
    }

    @Test
    void findBlogByBlogTitle() {
        Optional<BlogPost> blogPost = blogPostRepository.findByBlogTitle("TestTitle");

        assertTrue(blogPost.isPresent());
    }

    @Test
    void findBlogPostByBlogCategoryId() {
        List<BlogPost> blogPosts = blogPostRepository.findBlogPostByBlogCategoryId(8);

        assertEquals(1, blogPosts.size());
    }

    @Test
    void shouldNotSavePostWithSameTitle() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            blogPostRepository.save(createBlogPost("TestTitle", "Test blog.", new Date(), "Admin", createBlogCategory(1, "Art")));
        });
    }

    @Test
    void findBlogPostByBlogCategoryIdAndBlogTitleLike() {
        List<BlogPost> blogPosts = blogPostRepository.findBlogPostByBlogCategoryIdAndBlogTitleLike(1, "title");

        assertEquals(1, blogPosts.size());
    }

    @Test
    void findBlogPostByBlogTitleLike() {
        List<BlogPost> blogPosts = blogPostRepository.findBlogPostByBlogTitleLike("title");

        assertEquals(1, blogPosts.size());
    }

    private BlogPost createBlogPost(String title, String content, Date date, String author, BlogCategory blogCategory) {
        BlogPost blogPost = new BlogPost();
        blogPost.setBlogTitle(title);
        blogPost.setBlogContent(content);
        blogPost.setDateStamp(date);
        blogPost.setBlogAuthor(author);
        blogPost.setBlogCategory(blogCategory);
        return blogPost;
    }

    private BlogCategory createBlogCategory(Integer id, String name) {
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setId(id);
        blogCategory.setName(name);
        return blogCategory;
    }
}