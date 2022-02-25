package com.communardo.blogApp.datatransferobject;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogCategoryDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    public BlogCategoryDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

