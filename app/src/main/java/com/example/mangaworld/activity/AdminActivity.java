package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mangaworld.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void gotoUser(View view) {
        startActivity(new Intent(this, CRUDUser.class));
    }

    public void gotoAuthor(View view) { startActivity(new Intent(this, CRUDAuthorActivity.class)); }

    public void gotoType(View view) {
        startActivity(new Intent(this, CRUDBookTypes.class));
    }
    public void gotoChapter(View view) {
        startActivity(new Intent(this, CRUDChapter.class));
    }
}