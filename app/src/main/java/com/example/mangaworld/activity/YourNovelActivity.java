package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.mangaworld.R;

public class YourNovelActivity extends AppCompatActivity {

    Button btnYourNovelToAdding;
    ListView lvYourNovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_novel);
        setControl();
        setEvent();
    }

    private void setEvent() {
    }

    private void setControl() {
        btnYourNovelToAdding = findViewById(R.id.btnYourNovelToAdding);
        lvYourNovel = findViewById(R.id.lvYourNovel);
    }
}