package com.example.mangaworld.model;

public class Genre {
    private int idGenre;
    private String nameGenre;

    public Genre(int idGenre, String nameGenre) {
        this.idGenre = idGenre;
        this.nameGenre = nameGenre;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }
}
