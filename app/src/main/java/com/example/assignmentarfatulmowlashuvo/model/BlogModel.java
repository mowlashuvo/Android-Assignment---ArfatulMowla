package com.example.assignmentarfatulmowlashuvo.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BlogModel {

    @SerializedName("blogs")
    private List<Blog> blogs = null;

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

}
