package com.communardo.blogApp.datatransferobject;

import com.communardo.blogApp.model.BlogCategory;
import com.communardo.blogApp.model.BlogPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface MapStructMapper {
    BlogCategoryDTO categoryToBlogCategoryDTO(BlogCategory blogCategory);
    BlogCategory blogCategoryDtoToBlogCategory(BlogCategoryDTO blogCategoryDTO);
    @Mapping(source = "dateStamp", target = "dateStamp", dateFormat = "dd-MM-yyyy")
    BlogPostDTO blogPostToBlogPostDTO(BlogPost blogPost);
    BlogPost blogPostDtoToBlogPost(BlogPostDTO blogPostDTO);
    List<BlogPostDTO> blogPostToBlogPostDtoList(List<BlogPost> blogPostList);
}
