package com.communardo.blogApp.service;

import com.communardo.blogApp.datatransferobject.BlogPostDTO;
import com.communardo.blogApp.datatransferobject.MapStructMapper;
import com.communardo.blogApp.model.BlogCategory;
import com.communardo.blogApp.model.BlogPost;
import com.communardo.blogApp.repository.BlogPostRepository;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class BlogPostServiceTests {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private MapStructMapper mapper;

    @MockBean
    private BlogPostRepository blogPostRepository;


    @Test
    void getAllBlogPostsSuccessfully() {
        List<BlogPostDTO> blogPostDTOList = mapper.blogPostToBlogPostDtoList(blogPostRepository.findAll());

        List<BlogPostDTO> returnedListOfBlogPosts = blogPostService.getBlogPosts();

        assertSame(returnedListOfBlogPosts.size(), blogPostDTOList.size(), "Lists of Blog Posts should have the same size.");
    }

    @Test
    void getAllBlogPostsFailed() {
        List<BlogPostDTO> returnedListOfBlogPosts = blogPostService.getBlogPosts();

        assertSame(returnedListOfBlogPosts.size(), 0);
    }

    @Test
    void findBlogPostByIdSuccessfully() throws BlogServiceException {
        BlogPost mockBlogPost = createBlogPost("TestTitle", "Test blog.","Admin", createBlogCategory(1, "Art"));
        doReturn(Optional.of(mockBlogPost)).when(blogPostRepository).findById(100);

        Optional<BlogPost> returnedBlogPost = Optional.ofNullable(mapper.blogPostDtoToBlogPost(blogPostService.getBlogPostById(100)));

        assertTrue(returnedBlogPost.isPresent(), "BlogPost was not found");
        assertSame(returnedBlogPost.get().getBlogId(), mockBlogPost.getBlogId(), "Blog Posts should be the same.");
    }

    @Test
    @DisplayName("Save blog post service- Success")
    @WithMockUser(username = "admin", password = "12345678")
    void saveBlogPostSuccess() throws BlogServiceException {
        BlogPost blogPost = createBlogPost("TestTitle", "Test blog","Admin", createBlogCategory(1, "Art"));
        doReturn(blogPost).when(blogPostRepository).save(any());

        BlogPostDTO returnedBlogPostDTO = blogPostService.saveBlogPost(mapper.blogPostToBlogPostDTO(blogPost));

        Assertions.assertNotNull(returnedBlogPostDTO);
        Assertions.assertEquals(blogPost.getBlogTitle(), returnedBlogPostDTO.getBlogTitle());
    }

    @Test
    @DisplayName("Save blog post service- Fails")
    void saveBlogPostFailsNullBlog()  {
        BlogPost blogPost = null;
        doReturn(blogPost).when(blogPostRepository).save(blogPost);

        BlogPostDTO returnedBlogPostDTO = mapper.blogPostToBlogPostDTO(blogPost);

        Assertions.assertThrows(NullPointerException.class, () -> {
            blogPostService.saveBlogPost(returnedBlogPostDTO);
        });
    }

    @Test
    @DisplayName("Save blog post service- Fails Not unique title")
    void saveBlogPostFailsNonUniqueTitle() {
        BlogPost blogPost = createBlogPost("TestTitle", "Test blog","Admin", createBlogCategory(1, "Art"));
        doReturn(blogPost).when(blogPostRepository).save(blogPost);

        Mockito.doThrow(new DataValidationException("There should be unique titles"))
                .when(blogPostRepository).save(blogPost);
    }

    @Test
    @DisplayName("Update blog post service- Success")
    void updateBlogPostSuccessfully() throws BlogServiceException {
        BlogPost blogPost = createBlogPost("TestTitle", "Test blog","Admin", createBlogCategory(1, "Art"));
        blogPost.setBlogTitle("Updated title");
        doReturn(Optional.of(blogPost)).when(blogPostRepository).findById(100);

        Optional<BlogPost> returnedBlogPost = Optional.ofNullable(mapper.blogPostDtoToBlogPost(blogPostService.getBlogPostById(100)));

        assertSame(returnedBlogPost.get().getBlogTitle(), blogPost.getBlogTitle());
    }

    @Test
    @DisplayName("Update blog post service- Failed")
    void updateBlogPostFails() {
        BlogPost blogPost = createBlogPost("TestTitle", "Test blog","Admin", createBlogCategory(1, "Art"));
        blogPost.setBlogTitle("Updated Title");
        doReturn(Optional.empty()).when(blogPostRepository).findById(100);

        Optional<BlogPostDTO> returnedBlogPost = Optional.ofNullable(mapper.blogPostToBlogPostDTO(blogPost));

        assertNotEquals(returnedBlogPost, blogPost);
    }

    @Test
    @DisplayName("Delete blog post service- Success")
    void deleteBlogPostSuccessfully() throws BlogServiceException {
        BlogPost blogPost = createBlogPost("TestTitle", "Test blog","Admin", createBlogCategory(1, "Art"));
        doReturn(Optional.of(blogPost)).when(blogPostRepository).findById(100);
        blogPostService.deleteBlogPost(100);
        Mockito.verify(blogPostRepository).deleteById(100);
    }

    @Test
    @DisplayName("Delete blog post service- Fails")
    void deleteBlogPostThrowsException()  {
        doReturn(Optional.empty()).when(blogPostRepository).findById(anyInt());
        assertThrows(BlogServiceException.class, () -> {
            blogPostService.deleteBlogPost(10);
        });
        Mockito.verify(blogPostRepository,times(0)).deleteById(anyInt());
    }

    private BlogPost createBlogPost(String title, String content, String author, BlogCategory blogCategory) {
        BlogPost blogPost = new BlogPost();
        blogPost.setBlogId(100);
        blogPost.setBlogTitle(title);
        blogPost.setBlogContent(content);
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