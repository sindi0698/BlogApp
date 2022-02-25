package com.communardo.blogApp.datatransferobject;

import com.communardo.blogApp.model.BlogCategory;
import com.communardo.blogApp.model.BlogPost;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-20T15:51:08+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public BlogCategoryDTO categoryToBlogCategoryDTO(BlogCategory blogCategory) {
        if ( blogCategory == null ) {
            return null;
        }

        BlogCategoryDTO blogCategoryDTO = new BlogCategoryDTO();

        blogCategoryDTO.setId( blogCategory.getId() );
        blogCategoryDTO.setName( blogCategory.getName() );

        return blogCategoryDTO;
    }

    @Override
    public BlogCategory blogCategoryDtoToBlogCategory(BlogCategoryDTO blogCategoryDTO) {
        if ( blogCategoryDTO == null ) {
            return null;
        }

        BlogCategory blogCategory = new BlogCategory();

        blogCategory.setId( blogCategoryDTO.getId() );
        blogCategory.setName( blogCategoryDTO.getName() );

        return blogCategory;
    }

    @Override
    public BlogPostDTO blogPostToBlogPostDTO(BlogPost blogPost) {
        if ( blogPost == null ) {
            return null;
        }

        BlogPostDTO blogPostDTO = new BlogPostDTO();

        if ( blogPost.getDateStamp() != null ) {
            blogPostDTO.setDateStamp( new SimpleDateFormat( "dd-MM-yyyy" ).format( blogPost.getDateStamp() ) );
        }
        blogPostDTO.setBlogId( blogPost.getBlogId() );
        blogPostDTO.setBlogTitle( blogPost.getBlogTitle() );
        blogPostDTO.setBlogContent( blogPost.getBlogContent() );
        blogPostDTO.setBlogAuthor( blogPost.getBlogAuthor() );
        blogPostDTO.setBlogCategory( categoryToBlogCategoryDTO( blogPost.getBlogCategory() ) );

        return blogPostDTO;
    }

    @Override
    public BlogPost blogPostDtoToBlogPost(BlogPostDTO blogPostDTO) {
        if ( blogPostDTO == null ) {
            return null;
        }

        BlogPost blogPost = new BlogPost();

        blogPost.setBlogId( blogPostDTO.getBlogId() );
        blogPost.setBlogTitle( blogPostDTO.getBlogTitle() );
        blogPost.setBlogContent( blogPostDTO.getBlogContent() );
        try {
            if ( blogPostDTO.getDateStamp() != null ) {
                blogPost.setDateStamp( new SimpleDateFormat().parse( blogPostDTO.getDateStamp() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        blogPost.setBlogAuthor( blogPostDTO.getBlogAuthor() );
        blogPost.setBlogCategory( blogCategoryDtoToBlogCategory( blogPostDTO.getBlogCategory() ) );

        return blogPost;
    }

    @Override
    public List<BlogPostDTO> blogPostToBlogPostDtoList(List<BlogPost> blogPostList) {
        if ( blogPostList == null ) {
            return null;
        }

        List<BlogPostDTO> list = new ArrayList<BlogPostDTO>( blogPostList.size() );
        for ( BlogPost blogPost : blogPostList ) {
            list.add( blogPostToBlogPostDTO( blogPost ) );
        }

        return list;
    }
}
