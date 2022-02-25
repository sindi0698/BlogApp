package com.communardo.blogApp.repository;

import com.communardo.blogApp.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>{
    Optional<BlogPost> findByBlogTitle(String blogTitle);
    @Query("SELECT bp FROM BlogPost bp WHERE bp.blogCategory.id = :blogCategoryId")
    List<BlogPost> findBlogPostByBlogCategoryId(@Param("blogCategoryId") Integer blogCategoryId);
    @Query("SELECT bp FROM BlogPost bp WHERE bp.blogCategory.id = :blogCategoryId AND LOWER(bp.blogTitle) LIKE %:wordOfTitle% ")
    List<BlogPost> findBlogPostByBlogCategoryIdAndBlogTitleLike(@Param("blogCategoryId") Integer blogCategoryId, @Param("wordOfTitle") String wordOfTitle);
    @Query("SELECT bp FROM BlogPost bp WHERE LOWER(bp.blogTitle) LIKE %:wordOfTitle%")
    List<BlogPost> findBlogPostByBlogTitleLike(@Param("wordOfTitle") String wordOfTitle);
}
