package com.example.mangaworld.model;

public class NovelGenresModel {
    private int NovelID;
    private int GenreID;

    public NovelGenresModel() {
    }

    public NovelGenresModel(int novelID, int genreID) {
        NovelID = novelID;
        GenreID = genreID;
    }

    public int getNovelID() {
        return NovelID;
    }

    public void setNovelID(int novelID) {
        NovelID = novelID;
    }

    public int getGenreID() {
        return GenreID;
    }

    public void setGenreID(int genreID) {
        GenreID = genreID;
    }
}
