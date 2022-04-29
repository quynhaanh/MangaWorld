package com.example.mangaworld.model;

public class Manga {
    String link;
    String title; //name
    int view;
    String genre;

    public Manga(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public Manga(String link, String title, int price, String genre) {
        this.link = link;
        this.title = title;
        this.view = price;
        this.genre = genre;
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
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
