package com.communardo.blogApp.controller;

import com.communardo.blogApp.datatransferobject.BlogCategoryDTO;
import com.communardo.blogApp.datatransferobject.BlogPostDTO;
import com.communardo.blogApp.service.BlogPostService;
import com.communardo.blogApp.service.BlogServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BlogPostControllerTests {

    @MockBean
    private BlogPostService blogPostService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("GET /post/3 - Found")
    @WithMockUser(username = "admin", password = "12345678")
    void getBlogPostByIdIsFound() throws Exception {
        BlogCategoryDTO blogCategoryDTO = initializeBlogCategoryDTO();
        BlogPostDTO blogPostDTO = initializeBlogPostDTO(blogCategoryDTO);
        doReturn(blogPostDTO).when(blogPostService).getBlogPostById(3);

        mockMvc.perform(get("/api/post/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.responseObject.blogId", is(3)))
                .andExpect(jsonPath("$.responseObject.blogTitle", is("Blog Post Title Test")))
                .andExpect(jsonPath("$.responseObject.blogContent", is("Blog Post Content Test")))
                .andExpect(jsonPath("$.responseObject.dateStamp", is("17-02-2022")))
                .andExpect(jsonPath("$.responseObject.blogAuthor", is("Blog Post Author Test")))
                .andExpect(jsonPath("$.responseObject.blogCategory.id", is(1)));
    }


    @Test
    @DisplayName("GET /post/3 - Not Found")
    @WithMockUser(username = "admin", password = "12345678")
    void getBlogPostByIdIsNotFound() throws Exception {
        doReturn(null).when(blogPostService).getBlogPostById(100);
            mockMvc.perform(get("/api/post/{id}", 100))
                    .andExpect(status().isOk())
                    .andExpect(content().string("{\"message\":null,\"statusCode\":200,\"responseObject\":null}"));
    }

    @Test
    @DisplayName("POST /posts - SUCCESS")
    @WithMockUser(username = "admin", password = "12345678")
    void saveBlogPostSuccess() throws Exception {
        BlogCategoryDTO blogCategoryDTO = initializeBlogCategoryDTO();
        BlogPostDTO mockPostDTO = new BlogPostDTO();
        mockPostDTO.setBlogId(2);
        mockPostDTO.setBlogTitle("Save Test 2");
        mockPostDTO.setBlogContent("Blog Post Content Test");
        mockPostDTO.setDateStamp("17-02-2022");
        mockPostDTO.setBlogAuthor("Blog Post Author Test");
        mockPostDTO.setBlogCategory(blogCategoryDTO);

        doReturn(mockPostDTO).when(blogPostService).saveBlogPost(any());

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockPostDTO)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.statusCode", is(201)))
                .andExpect(jsonPath("$.responseObject.blogId", is(2)))
                .andExpect(jsonPath("$.responseObject.blogTitle", is("Save Test 2")))
                .andExpect(jsonPath("$.responseObject.blogContent", is("Blog Post Content Test")))
                .andExpect(jsonPath("$.responseObject.dateStamp", is("17-02-2022")))
                .andExpect(jsonPath("$.responseObject.blogAuthor", is("Blog Post Author Test")))
                .andExpect(jsonPath("$.responseObject.blogCategory.id", is(1)));
    }



    @Test
    @DisplayName("PUT /posts - SUCCESS")
    @WithMockUser(username = "admin", password = "12345678")
    void updateBlogPostSuccess() throws Exception {
        BlogCategoryDTO blogCategoryDTO = initializeBlogCategoryDTO();
        BlogPostDTO putBlogPost = new BlogPostDTO();
        putBlogPost.setBlogId(3);
        putBlogPost.setBlogTitle("Updated title 3");
        putBlogPost.setBlogContent("Blog Post Content Test");
        putBlogPost.setDateStamp("17-02-2022");
        putBlogPost.setBlogAuthor("Blog Post Author Test");
        putBlogPost.setBlogCategory(blogCategoryDTO);

        doReturn(putBlogPost).when(blogPostService).updateBlogPost(any());

        mockMvc.perform(put("/api/posts", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putBlogPost)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.responseObject.blogId", is(3)))
                .andExpect(jsonPath("$.responseObject.blogTitle", is("Updated title 3")))
                .andExpect(jsonPath("$.responseObject.blogContent", is("Blog Post Content Test")))
                .andExpect(jsonPath("$.responseObject.dateStamp", is("17-02-2022")))
                .andExpect(jsonPath("$.responseObject.blogAuthor", is("Blog Post Author Test")))
                .andExpect(jsonPath("$.responseObject.blogCategory.id", is(1)));

    }

    @Test
    @DisplayName("PUT /posts - Fails")
    @WithMockUser(username = "admin", password = "12345678")
    void updateBlogPostNotFound() throws Exception {
        BlogCategoryDTO blogCategoryDTO = initializeBlogCategoryDTO();
        BlogPostDTO blogPostDTO = initializeBlogPostDTO(blogCategoryDTO);
        doReturn(null).when(blogPostService).updateBlogPost(any());

        mockMvc.perform(put("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blogPostDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":null,\"statusCode\":200,\"responseObject\":null}"));

    }
    @Test
    @DisplayName("DELETE /posts - Successful")
    @WithMockUser(username = "admin", password = "12345678")
    void deletePostSuccessfully() throws Exception{

        Mockito.doReturn(null).when(blogPostService).getBlogPostById(anyInt());
        mockMvc.perform(delete("/api/posts/{id}",1))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("DELETE /posts - Failed")
    @WithMockUser(username = "admin", password = "12345678")
    void deletePostFailed() throws Exception{

        Mockito.doThrow(new BlogServiceException("A non existing blog post can not be found!")).when(blogPostService).deleteBlogPost(anyInt());
        mockMvc.perform(delete("/api/posts/{id}",399))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"A non existing blog post can not be found!\",\"statusCode\":404,\"responseObject\":null}"));

    }

    private BlogPostDTO initializeBlogPostDTO(BlogCategoryDTO blogCategoryDTO) {
        BlogPostDTO blogPostDTO = new BlogPostDTO();
        blogPostDTO.setBlogId(3);
        blogPostDTO.setBlogTitle("Blog Post Title Test");
        blogPostDTO.setBlogContent("Blog Post Content Test");
        blogPostDTO.setDateStamp("17-02-2022");
        blogPostDTO.setBlogAuthor("Blog Post Author Test");
        blogPostDTO.setBlogCategory(blogCategoryDTO);
        return blogPostDTO;
    }

    private BlogCategoryDTO initializeBlogCategoryDTO(){
        BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();
        blogCategoryDTO.setId(1);
        blogCategoryDTO.setName("Test");
        return blogCategoryDTO;
    }

    static String asJsonString(final Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}