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
        startActivity(new Intent(this, CRUDUserActivity.class));
    }

    public void gotoNovel(View view){
        Intent intent = new Intent(this, YourNovelActivity.class);
        intent.putExtra("adminMode",true);
        startActivity(intent);
    }

    public void gotoAuthor(View view) {
        startActivity(new Intent(this, CRUDAuthorActivity.class));
    }

    public void gotoGenre(View view) {

        startActivity(new Intent(this, CRUDGenreActivity.class));
    }

    public void gotoChart(View view) {
        startActivity(new Intent(this, ChartActivity.class));
    }

    public void closeActivity(View view) {
        finish();
    }
}