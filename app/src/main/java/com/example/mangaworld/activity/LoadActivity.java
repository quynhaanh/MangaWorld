package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mangaworld.R;

public class LoadActivity extends AppCompatActivity {
<<<<<<< HEAD
    public static String url = "http://192.168.1.2";
=======
    public static String url = "http://192.168.1.7";
>>>>>>> 9c5d02aa6bf69502a3550df66ca12cf51cd10f4d
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent intent = new Intent(LoadActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}