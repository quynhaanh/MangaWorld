package com.example.mangaworld.model;

public class NovelModel {
    private int id;
    private String title;
    private int idAuthor;
    private String description;
    private String cover;
    private String datePost;
    private String idUser;
    private String coverImageData;

    public NovelModel() {
    }

    public NovelModel(int id, String title, int idAuthor, String description, String cover,
                      String datePost, String idUser, String coverImageData) {
        this.id = id;
        this.title = title;
        this.idAuthor = idAuthor;
        this.description = description;
        this.cover = cover;
        this.datePost = datePost;
        this.idUser = idUser;
        this.coverImageData = coverImageData;
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

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getCoverImageData() {
        return coverImageData;
    }

    public void setCoverImageData(String coverImageData) {
        this.coverImageData = coverImageData;
    }
}
