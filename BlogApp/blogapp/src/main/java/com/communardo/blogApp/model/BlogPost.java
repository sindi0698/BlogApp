package com.communardo.blogApp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "blogpost")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Integer blogId;

    @Column(name = "blog_title", nullable = false, unique = true)
    private String blogTitle;

    @Column(name = "blog_content", nullable = false)
    private String blogContent;

    @Column(name = "date_stamp")
    private Date dateStamp;

    @Column(name = "blog_author", nullable = false)
    private String blogAuthor;

    @ManyToOne
    @JoinColumn(name = "blog_category_id", referencedColumnName = "category_id", nullable = false)
    private BlogCategory blogCategory;

    public BlogPost() {
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

    public Date getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(Date dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getBlogAuthor() {
        return blogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        this.blogAuthor = blogAuthor;
    }

    public BlogCategory getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(BlogCategory blogCategory) {
        this.blogCategory = blogCategory;
    }
}
