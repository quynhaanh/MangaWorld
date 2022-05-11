package com.example.mangaworld.model;

import java.util.Date;

public class NovelModel {
    private int id;
    private String title;
    private int AuthorID;
    private String cover;
    private Date datePost;
    private String UserID;

    public NovelModel() {
    }

    public NovelModel(int id, String title, int authorID, String cover, Date datePost, String userID) {
        this.id = id;
        this.title = title;
        AuthorID = authorID;
        this.cover = cover;
        this.datePost = datePost;
        UserID = userID;
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

    public int getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(int authorID) {
        AuthorID = authorID;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
