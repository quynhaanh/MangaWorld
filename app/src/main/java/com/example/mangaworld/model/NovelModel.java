package com.example.mangaworld.model;

import java.util.Date;

public class NovelModel {
    private int ID;
    private String title;
    private int IDAuthor;
    private String cover;
    private String datePost;
    private String IDUser;

    public NovelModel() {
    }

    public NovelModel(int ID, String title, int IDAuthor, String cover, String datePost, String IDUser) {
        this.ID = ID;
        this.title = title;
        this.IDAuthor = IDAuthor;
        this.cover = cover;
        this.datePost = datePost;
        this.IDUser = IDUser;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIDAuthor() {
        return IDAuthor;
    }

    public void setIDAuthor(int IDAuthor) {
        this.IDAuthor = IDAuthor;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }
}
