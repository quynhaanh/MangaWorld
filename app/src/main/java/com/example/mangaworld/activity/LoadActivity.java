package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mangaworld.R;

public class LoadActivity extends AppCompatActivity {
<<<<<<< HEAD
    public static String url = "http://192.168.1.8";
=======
    public static String url = "http://192.168.1.7";
>>>>>>> dc66f79094c41a5f21b0783cc6392f34c132ed9d
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent intent = new Intent(LoadActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}