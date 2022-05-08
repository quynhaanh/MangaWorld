package com.example.mangaworld.model;

public class Manga {

    private int idManga;
    private String link; //thumb
    private String title; //name
    private String type;
    private String endpoint;
    private String upload_on;
    private String update_on;
    private Boolean favorite;
    private String recents;
    private int status;
    private int view;


    public Manga(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public Manga(int id, String link, String title, int view, String typee) {
        this.idManga = id;
        this.link = link;
        this.title = title;
        this.view = view;
        this.type = typee;
    }

    public Manga(int idManga, String link, String title, String type, String endpoint, String upload_on, String update_on, Boolean favorite, String recents, int status, int view) {
        this.idManga = idManga;
        this.link = link;
        this.title = title;
        this.type = type;
        this.endpoint = endpoint;
        this.upload_on = upload_on;
        this.update_on = update_on;
        this.favorite = favorite;
        this.recents = recents;
        this.status = status;
        this.view = view;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return view;
    }

    public void setPrice(int price) {
        this.view = price;
    }

    public String getGenre() {
        return type;
    }

    public void setGenre(String genre) {
        this.type = genre;
    }

    public int getIdManga() {
        return idManga;
    }

    public void setIdManga(int idManga) {
        this.idManga = idManga;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUpload_on() {
        return upload_on;
    }

    public void setUpload_on(String upload_on) {
        this.upload_on = upload_on;
    }

    public String getUpdate_on() {
        return update_on;
    }

    public void setUpdate_on(String update_on) {
        this.update_on = update_on;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getRecents() {
        return recents;
    }

    public void setRecents(String recents) {
        this.recents = recents;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
