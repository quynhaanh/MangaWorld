package com.example.mangaworld.model;

import java.util.Date;

public class ChapterModel {
    private int id;
    private String title;
    private String Content;
    private String datePost;
    private int NovelID;

    public ChapterModel() {
    }

    public ChapterModel(int id, String title, String content, String datePost, int novelID) {
        this.id = id;
        this.title = title;
        Content = content;
        this.datePost = datePost;
        NovelID = novelID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public int getNovelID() {
        return NovelID;
    }

    public void setNovelID(int novelID) {
        NovelID = novelID;
    }
}
