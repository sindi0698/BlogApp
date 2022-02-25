package com.communardo.blogApp.controller;

import com.communardo.blogApp.datatransferobject.BlogPostDTO;
import com.communardo.blogApp.response.CustomResponse;
import com.communardo.blogApp.service.BlogPostService;
import com.communardo.blogApp.service.BlogServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api")
public class BlogPostController {

    private final Logger logger = LoggerFactory.getLogger(BlogPostController.class);

    @Autowired
    BlogPostService blogPostService;

    @GetMapping("/posts")
    public CustomResponse<List<BlogPostDTO>> findAllBlogPosts() {
            List<BlogPostDTO> blogPostDTO = blogPostService.getBlogPosts();
        return new CustomResponse.CustomResponseBuilder<List<BlogPostDTO>>().setResponseObject(blogPostDTO)
                            .setStatusCode(HttpStatus.OK.value()).build();
        }


    @GetMapping("/post/{id}")
    public CustomResponse<BlogPostDTO> getBlogPostById(@PathVariable Integer id) {
        try {
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>().setResponseObject(blogPostService.getBlogPostById(id)).
                    setStatusCode(HttpStatus.OK.value()).build();
        } catch (BlogServiceException blogServiceException) {
            logger.error("A non existing id is trying to be found!");
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>().setStatusCode(HttpStatus.NOT_FOUND.value()).
                    setMessage(blogServiceException.getMessage()).build();
        }
    }

    @PostMapping("/posts")
    public CustomResponse<BlogPostDTO> savePost(@RequestBody BlogPostDTO blogPostDTO) {
        try {
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>().setResponseObject(blogPostService.saveBlogPost(blogPostDTO))
                    .setStatusCode(HttpStatus.CREATED.value()).build();
        } catch (BlogServiceException blogServiceException) {
            logger.error( "Bad request sent for saving the blog post, checkout attributes!");
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>()
                    .setStatusCode(HttpStatus.BAD_REQUEST.value()).setMessage(blogServiceException.getMessage()).build();
        } catch (Exception exception) {
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>()
                    .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage("There was sth wrong. Please try again.").build();
        }
    }

    @PutMapping("/posts")
    public CustomResponse<BlogPostDTO> updatePost(@RequestBody BlogPostDTO blogPostDTO) {
        try {
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>().setResponseObject(blogPostService.updateBlogPost(blogPostDTO))
                    .setStatusCode(HttpStatus.OK.value()).build();
        } catch (BlogServiceException blogServiceException) {
            logger.error("Bad request sent for updating the blog post, checkout attributes!");
            return new CustomResponse.CustomResponseBuilder<BlogPostDTO>()
                    .setStatusCode(HttpStatus.BAD_REQUEST.value()).setMessage(blogServiceException.getMessage()).build();
        }
    }

    @DeleteMapping("/posts/{id}")
    public CustomResponse<Void> deletePost(@PathVariable("id") Integer blogId) {
        try {
            blogPostService.deleteBlogPost(blogId);
            return new CustomResponse.CustomResponseBuilder<Void>().setMessage("Post deleted successfully!")
                    .setStatusCode(HttpStatus.OK.value()).build();
        } catch (BlogServiceException blogServiceException) {
            logger.error("A non existing blog post can not be found!");
            return new CustomResponse.CustomResponseBuilder<Void>().setMessage(blogServiceException.getMessage())
                    .setStatusCode(HttpStatus.NOT_FOUND.value()).build();
        }
    }

    @GetMapping("/search")
    public CustomResponse<Object> getFilteredPosts(
            @RequestParam(name = "filteredCategoryId", required = false) Integer filteredCategoryId,
            @RequestParam(name = "filteredTitle", required = false) String filteredTitle) {
        List<BlogPostDTO> blogPostDTOList = null;
        try {
            blogPostDTOList = blogPostService.getFilteredPosts(filteredTitle, filteredCategoryId);

        } catch (BlogServiceException blogServiceException) {
            logger.error("Take note on passing filter query parameters!");
            return new CustomResponse.CustomResponseBuilder<>().setMessage(blogServiceException.getMessage())
                    .setStatusCode(HttpStatus.NOT_FOUND.value()).build();
        }
        return new CustomResponse.CustomResponseBuilder<>().setResponseObject(blogPostDTOList).setStatusCode(HttpStatus.OK.value()).build();
    }
}
