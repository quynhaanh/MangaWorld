package com.example.mangaworld.model;

public class Chapter {
    private int idChapter;
    private String chapter_endpoint;
    private String chapter_title;
    private String chapter_thumb;
    private Boolean lock;

    public Chapter(int idChapter, String chapter_endpoint, String chapter_title, String chapter_thumb, Boolean lock) {
        this.idChapter = idChapter;
        this.chapter_endpoint = chapter_endpoint;
        this.chapter_title = chapter_title;
        this.chapter_thumb = chapter_thumb;
        this.lock = lock;
    }

    public String getChapter_endpoint() {
        return chapter_endpoint;
    }

    public void setChapter_endpoint(String chapter_endpoint) {
        this.chapter_endpoint = chapter_endpoint;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public String getChapter_thumb() {
        return chapter_thumb;
    }

    public void setChapter_thumb(String chapter_thumb) {
        this.chapter_thumb = chapter_thumb;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public int getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(int idChapter) {
        this.idChapter = idChapter;
    }
}
