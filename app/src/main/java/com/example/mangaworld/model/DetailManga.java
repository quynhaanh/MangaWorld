package com.example.mangaworld.model;

import java.util.List;

public class DetailManga {
    private int idManga;
    private int status;
    private String author;
    private String synopsis;
    private List<Chapter> chapterList;
    private List<Genre> genreList;

    public DetailManga(int idManga, int status, String author, String synopsis, List<Chapter> chapterList, List<Genre> genreList) {
        this.idManga = idManga;
        this.status = status;
        this.author = author;
        this.synopsis = synopsis;
        this.chapterList = chapterList;
        this.genreList = genreList;
    }

    public int getIdManga() {
        return idManga;
    }

    public void setIdManga(int idManga) {
        this.idManga = idManga;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
}
