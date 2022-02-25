package com.communardo.blogApp.datatransferobject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogPostDTO {
    @JsonProperty("blogId")
    private Integer blogId;
    @JsonProperty("blogTitle")
    private String blogTitle;
    @JsonProperty("blogContent")
    private String blogContent;
    @JsonProperty("dateStamp")
    private String dateStamp;
    @JsonProperty("blogAuthor")
    private String blogAuthor;
    @JsonProperty("blogCategory")
    private BlogCategoryDTO blogCategory;

    public BlogPostDTO() {
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getBlogAuthor() {
        return blogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        this.blogAuthor = blogAuthor;
    }

    public BlogCategoryDTO getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(BlogCategoryDTO blogCategory) {
        this.blogCategory = blogCategory;
    }
}
