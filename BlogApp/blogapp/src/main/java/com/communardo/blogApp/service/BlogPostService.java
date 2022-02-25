package com.communardo.blogApp.service;

import com.communardo.blogApp.datatransferobject.BlogPostDTO;
import com.communardo.blogApp.datatransferobject.MapStructMapper;
import com.communardo.blogApp.model.BlogPost;
import com.communardo.blogApp.repository.BlogPostRepository;
import com.communardo.blogApp.utils.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private MapStructMapper mapper;

    public List<BlogPostDTO> getBlogPosts() {
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        return mapper.blogPostToBlogPostDtoList(blogPostList);
    }

    public BlogPostDTO getBlogPostById(Integer blogId) throws BlogServiceException {
        if (blogPostRepository.findById(blogId).isPresent()) {
            return mapper.blogPostToBlogPostDTO(blogPostRepository.findById(blogId).get());
        } else {
            throw new BlogServiceException("There is no blogpost with this id.");
        }
    }

    public BlogPostDTO saveBlogPost(BlogPostDTO blogPostDTO) throws BlogServiceException {
        if (blogPostDTO.getBlogTitle() == null || blogPostDTO.getBlogTitle().isEmpty()) {
            throw new BlogServiceException("You can not save a blog with no title.");
        } else if (blogPostRepository.findByBlogTitle(blogPostDTO.getBlogTitle()).isPresent()) {
            throw new BlogServiceException("The blog title you entered already exists! Titles are unique, try another one.");
        } else {
            BlogPost blogPost = mapper.blogPostDtoToBlogPost(blogPostDTO);
            blogPost.setDateStamp(new Date());
            blogPost.setBlogAuthor(AuthenticationHelper.getAuthenticationName());
            return mapper.blogPostToBlogPostDTO(blogPostRepository.save(blogPost));
        }
    }

    public BlogPostDTO updateBlogPost(BlogPostDTO blogPostDTO) throws BlogServiceException {
        BlogPost updatedBlog = mapper.blogPostDtoToBlogPost(blogPostDTO);
        BlogPostDTO existingBlogPostFromDb = getBlogPostById(blogPostDTO.getBlogId());
        BlogPostDTO newBlogPost;
        if (blogPostRepository.findById(updatedBlog.getBlogId()).isPresent()) {
            if (blogPostRepository.findByBlogTitle(updatedBlog.getBlogTitle()).isPresent() && existingBlogPostFromDb.getBlogTitle().equals(updatedBlog.getBlogTitle())) {
                throw new BlogServiceException("The blog title you entered already exists! Titles are unique, try another one.");
            }
            updatedBlog.setDateStamp(new Date());
            updatedBlog.setBlogAuthor(AuthenticationHelper.getAuthenticationName());
            newBlogPost = mapper.blogPostToBlogPostDTO(blogPostRepository.save(updatedBlog));
        } else {
            throw new BlogServiceException("You are trying to update a non existing blog post.");
        }
        return newBlogPost;
    }

    public void deleteBlogPost(Integer blogId) throws BlogServiceException {
        if (blogPostRepository.findById(blogId).isPresent()) {
            blogPostRepository.deleteById(blogId);
        } else {
            throw new BlogServiceException("This blog post does not exist so it can not be deleted.");
        }
    }

    public List<BlogPostDTO> getFilteredPosts(String filteredTitle, Integer filteredCategoryId) throws BlogServiceException {
        List<BlogPost> blogPosts;
        if (filteredCategoryId == 0 && filteredTitle == null) {
            blogPosts = blogPostRepository.findAll();
        } else if (filteredCategoryId != 0 && filteredTitle == null) {
            blogPosts = blogPostRepository.findBlogPostByBlogCategoryId(filteredCategoryId);
        } else if (filteredCategoryId == 0) {
            blogPosts = blogPostRepository.findBlogPostByBlogTitleLike(filteredTitle);
        } else {
            blogPosts = blogPostRepository.findBlogPostByBlogCategoryIdAndBlogTitleLike(filteredCategoryId, filteredTitle);
        }
        if (blogPosts.isEmpty()) {
            throw new BlogServiceException("There are no matching blogposts.");
        }
        return mapper.blogPostToBlogPostDtoList(blogPosts);
    }
}
