package com.rexontechnologies.popularmovies.utils;

import android.text.TextUtils;

public class Review {

    private String author;
    private String content;
    private String id;


    public Review() {
        this.author = author;
        this.content = content;
        this.id = id;
    }

    public String getAuthor() {
        return TextUtils.isEmpty(author) ? "N/A" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent(){
        return TextUtils.isEmpty(content)?"N/A" : content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getId(){
        return TextUtils.isEmpty(id)?"N/A" : id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
