package com.example.mangaworld.model;

public class UserModel {
    String id, name, email,pass;
    int idRole;

    public UserModel(String id, String name, String email, String pass, int idRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.idRole = idRole;
    }

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
