package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.mangaworld.R;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;

public class YourNovelActivity extends AppCompatActivity {

    Button btnYourNovelToAdding;
    ListView lvYourNovel;

    NovelController novelController;

    String url = LoadActivity.url;

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

        novelController = new NovelController(url, this);

    }

    public void refreshListYourNovel()
    {
        novelController.getNovel(new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }
        });
    }
}