package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mangaworld.R;

public class LoadActivity extends AppCompatActivity {
    public static String url = "http://192.168.1.8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent intent = new Intent(LoadActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}